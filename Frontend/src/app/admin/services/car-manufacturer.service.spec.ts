import { TestBed } from '@angular/core/testing';

import { CarManufacturerService } from './car-manufacturer.service';

describe('CarManufacturerService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CarManufacturerService = TestBed.get(CarManufacturerService);
    expect(service).toBeTruthy();
  });
});
