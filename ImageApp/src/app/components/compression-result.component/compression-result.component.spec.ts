import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompressionResultComponent } from './compression-result.component';

describe('CompressionResultComponent', () => {
  let component: CompressionResultComponent;
  let fixture: ComponentFixture<CompressionResultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompressionResultComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CompressionResultComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
