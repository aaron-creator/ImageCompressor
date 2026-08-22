import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-image-preview',
  imports: [CommonModule],
  templateUrl: './image-preview.component.html',
  styleUrl: './image-preview.component.scss'
})
export class ImagePreviewComponent {

  @Input() previewUrl: string | null = null;
  @Input() selectedFile: File | null = null;

  get fileSize(): string {
    if (!this.selectedFile) {
      return '';
    }

    const sizeInKb = this.selectedFile.size / 1024;
    const sizeInMb = sizeInKb / 1024;

    if (sizeInMb >= 1) {
      return `${sizeInMb.toFixed(2)} MB`;
    }

    return `${sizeInKb.toFixed(2)} KB`;
  }
}