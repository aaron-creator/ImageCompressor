import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { ImageCompressionResponse } from '../../services/image-compression.service';

@Component({
  selector: 'app-compression-result',
  imports: [CommonModule],
  templateUrl: './compression-result.component.html',
  styleUrl: './compression-result.component.scss'
})
export class CompressionResultComponent {

  @Input() result: ImageCompressionResponse | null = null;
  @Input() downloadUrl: string | null = null;

  get reductionPercentage(): string {
    if (!this.result) {
      return '';
    }

    const original = this.convertToKb(this.result.originalSize);
    const compressed = this.convertToKb(this.result.compressedSize);

    if (original === 0) {
      return '0';
    }

    const reduction = ((original - compressed) / original) * 100;
    return reduction.toFixed(2);
  }

  private convertToKb(size: string): number {
    const value = parseFloat(size);

    if (size.toLowerCase().includes('mb')) {
      return value * 1024;
    }

    return value;
  }
}