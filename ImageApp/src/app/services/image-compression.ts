import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ImageCompressionResponse {
  fileName: string;
  originalSize: string;
  compressedSize: string;
  message: string;
}

@Injectable({
  providedIn: 'root'
})
export class ImageCompression {

  private readonly baseUrl = 'http://localhost:8080/api/images';

  constructor(private http: HttpClient) {}

  getCompressionDetails(
    file: File,
    maxWidth: number,
    maxHeight: number,
    quality: number
  ): Observable<ImageCompressionResponse> {

    const formData = new FormData();
    formData.append('file', file);
    formData.append('maxWidth', maxWidth.toString());
    formData.append('maxHeight', maxHeight.toString());
    formData.append('quality', quality.toString());

    return this.http.post<ImageCompressionResponse>(
      `${this.baseUrl}/compress/details`,
      formData
    );
  }

  compressAndDownload(
    file: File,
    maxWidth: number,
    maxHeight: number,
    quality: number
  ): Observable<Blob> {

    const formData = new FormData();
    formData.append('file', file);
    formData.append('maxWidth', maxWidth.toString());
    formData.append('maxHeight', maxHeight.toString());
    formData.append('quality', quality.toString());

    return this.http.post(`${this.baseUrl}/compress`, formData, {
      responseType: 'blob'
    });
  }
}