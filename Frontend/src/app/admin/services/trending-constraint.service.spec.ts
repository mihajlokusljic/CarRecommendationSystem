import { TestBed } from '@angular/core/testing';

import { TrendingConstraintService } from './trending-constraint.service';

describe('TrendingConstraintService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TrendingConstraintService = TestBed.get(TrendingConstraintService);
    expect(service).toBeTruthy();
  });
});
