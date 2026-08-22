import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ImagePreviewComponent } from '../image-preview.component/image-preview.component';
import { CompressionResultComponent } from '../compression-result.component/compression-result.component';


import {
  ImageCompressionResponse,
  ImageCompressionService
} from '../../services/image-compression.service';


@Component({
  selector: 'app-image-upload',
  imports: [
    CommonModule,
    FormsModule,
    ImagePreviewComponent,
    CompressionResultComponent
  ],
  templateUrl: './image-upload.component.html',
  styleUrl: './image-upload.component.scss'
})
export class ImageUploadComponent {

  selectedFile: File | null = null;
  previewUrl: string | null = null;

  maxWidth = 1200;
  maxHeight = 1200;
  quality = 0.5;

  result: ImageCompressionResponse | null = null;
  downloadUrl: string | null = null;

  isLoading = false;
  errorMessage = '';

  constructor(private imageCompressionService: ImageCompressionService) {}

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;

    if (!input.files || input.files.length === 0) {
      return;
    }

    const file = input.files[0];

    if (!file.type.startsWith('image/')) {
      this.errorMessage = 'Please select a valid image file.';
      return;
    }

    this.selectedFile = file;
    this.result = null;
    this.errorMessage = '';

    if (this.previewUrl) {
      URL.revokeObjectURL(this.previewUrl);
    }

    this.previewUrl = URL.createObjectURL(file);
  }

  compressImage(): void {
    if (!this.selectedFile) {
      this.errorMessage = 'Please select an image first.';
      return;
    }

    if (this.quality < 0 || this.quality > 1) {
      this.errorMessage = 'Quality must be between 0.0 and 1.0';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';
    this.result = null;

    this.imageCompressionService.getCompressionDetails(
      this.selectedFile,
      this.maxWidth,
      this.maxHeight,
      this.quality
    ).subscribe({
      next: (response) => {
        this.result = response;
        this.getCompressedImageForDownload();
      },
      error: () => {
        this.isLoading = false;
        this.errorMessage = 'Failed to compress image. Please check backend server.';
      }
    });
  }

  private getCompressedImageForDownload(): void {
    if (!this.selectedFile) {
      return;
    }

    this.imageCompressionService.compressAndDownload(
      this.selectedFile,
      this.maxWidth,
      this.maxHeight,
      this.quality
    ).subscribe({
      next: (blob) => {
        if (this.downloadUrl) {
          URL.revokeObjectURL(this.downloadUrl);
        }

        this.downloadUrl = URL.createObjectURL(blob);
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
        this.errorMessage = 'Details created, but image download failed.';
      }
    });
  }
}