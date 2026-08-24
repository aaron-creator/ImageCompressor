import { CommonModule } from '@angular/common';
import { Component,ChangeDetectorRef } from '@angular/core';
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

  constructor(
    private imageCompressionService: ImageCompressionService,
    private changeDetectorRef: ChangeDetectorRef
  ) { }

  onFileSelected(event: Event): void {

    const input =
      event.target as HTMLInputElement;

    if (!input.files ||
      input.files.length === 0) {

      return;
    }

    const file = input.files[0];

    // Validate image
    if (!file.type.startsWith('image/')) {

      this.errorMessage =
        'Please select a valid image file.';

      return;
    }

    // Store selected file
    this.selectedFile = file;

    // Clear previous result
    this.result = null;

    // Clear previous error
    this.errorMessage = '';

    // Remove old preview URL
    if (this.previewUrl) {
      URL.revokeObjectURL(this.previewUrl);
    }

    // Remove old download URL
    if (this.downloadUrl) {
      URL.revokeObjectURL(this.downloadUrl);
      this.downloadUrl = null;
    }

    // Create new preview URL
    this.previewUrl =
      URL.createObjectURL(file);
  }

  compressImage(): void {

    // Check file
    if (!this.selectedFile) {

      this.errorMessage =
        'Please select an image first.';

      return;
    }

    // Check quality
    if (this.quality < 0 ||
      this.quality > 1) {

      this.errorMessage =
        'Quality must be between 0.0 and 1.0';

      return;
    }

    // Check dimensions
    if (this.maxWidth <= 0 ||
      this.maxHeight <= 0) {

      this.errorMessage =
        'Width and height must be greater than 0';

      return;
    }

    // Start loading
    this.isLoading = true;

    this.errorMessage = '';

    // Clear previous result
    this.result = null;

    // Remove previous download URL
    if (this.downloadUrl) {

      URL.revokeObjectURL(
        this.downloadUrl
      );

      this.downloadUrl = null;
    }

    // Call backend
    this.imageCompressionService
      .compressImage(
        this.selectedFile,
        this.maxWidth,
        this.maxHeight,
        this.quality
      )
      .subscribe({

        next: (blob) => {

          console.log(
            'Compressed image received:',
            blob
          );

          // Create download URL
          this.downloadUrl =
            URL.createObjectURL(blob);

          // Original file size
          const originalSize =
            this.selectedFile!.size;

          // Compressed file size
          const compressedSize =
            blob.size;

          console.log(
            'Original size:',
            originalSize
          );

          console.log(
            'Compressed size:',
            compressedSize
          );

          // Create result object
          this.result = {

            fileName:
              this.selectedFile!.name,

            originalSize:
              this.formatSize(
                originalSize
              ),

            compressedSize:
              this.formatSize(
                compressedSize
              ),

            message:
              'Image compressed successfully'
          };

          console.log(
            'Compression Result:',
            this.result
          );

          // Stop loading
          this.isLoading = false;
          this.changeDetectorRef.markForCheck();
        },

        error: (error) => {

          console.error(
            'Compression error:',
            error
          );

          this.isLoading = false;

          this.changeDetectorRef.markForCheck();
          this.errorMessage =
            'Failed to compress image. Please check backend server.';
        }
      });
  }

  private formatSize(bytes: number): string {

    if (bytes < 1024) {

      return `${bytes} Bytes`;
    }

    const kb =
      bytes / 1024;

    if (kb < 1024) {

      return `${kb.toFixed(2)} KB`;
    }

    const mb =
      kb / 1024;

    return `${mb.toFixed(2)} MB`;
  }
}