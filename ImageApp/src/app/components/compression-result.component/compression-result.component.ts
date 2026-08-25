import { CommonModule } from '@angular/common';
import {
  Component,
  EventEmitter,
  Input,
  Output
} from '@angular/core';

import {
  ImageCompressionResponse
} from '../../services/image-compression.service';

@Component({
  selector: 'app-compression-result',
  imports: [CommonModule],
  templateUrl: './compression-result.component.html',
  styleUrl: './compression-result.component.scss'
})
export class CompressionResultComponent {

  // Compression details received from ImageUploadComponent
  @Input()
  result: ImageCompressionResponse | null = null;

  // URL of the compressed image
  @Input()
  downloadUrl: string | null = null;

  // Sends Pay & Download click event to ImageUploadComponent
  @Output()
  payDownload = new EventEmitter<void>();


  /**
   * Called when user clicks Pay & Download.
   * The actual Razorpay logic is handled by ImageUploadComponent.
   */
  onPayAndDownload(): void {

    if (!this.downloadUrl) {
      return;
    }

    this.payDownload.emit();
  }


  /**
   * Calculates percentage reduction in image size.
   */
  get reductionPercentage(): string {

    if (!this.result) {
      return '0';
    }

    const original =
      this.convertToKb(this.result.originalSize);

    const compressed =
      this.convertToKb(this.result.compressedSize);

    if (original <= 0) {
      return '0';
    }

    const reduction =
      ((original - compressed) / original) * 100;

    return Math.max(0, reduction).toFixed(2);
  }


  /**
   * Converts size strings such as:
   *
   * 700.97 KB → 700.97
   * 1.5 MB    → 1536 KB
   * 500 Bytes → 0.488 KB
   */
  private convertToKb(size: string): number {

    const value = parseFloat(size);

    if (Number.isNaN(value)) {
      return 0;
    }

    const normalizedSize = size.toLowerCase();

    if (normalizedSize.includes('mb')) {
      return value * 1024;
    }

    if (normalizedSize.includes('bytes')) {
      return value / 1024;
    }

    return value;
  }
}