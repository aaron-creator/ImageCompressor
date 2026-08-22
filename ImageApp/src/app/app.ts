import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ImageUploadComponent } from './components/image-upload.component/image-upload.component';

@Component({
  selector: 'app-root',
  imports: [ImageUploadComponent],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected readonly title = signal('ImageApp');
}
