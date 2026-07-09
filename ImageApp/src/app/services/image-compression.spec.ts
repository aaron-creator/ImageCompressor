import { TestBed } from '@angular/core/testing';

import { ImageCompression } from './image-compression';

describe('ImageCompression', () => {
  let service: ImageCompression;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ImageCompression);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
