import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrendingConstraintConfigComponent } from './trending-constraint-config.component';

describe('TrendingConstraintConfigComponent', () => {
  let component: TrendingConstraintConfigComponent;
  let fixture: ComponentFixture<TrendingConstraintConfigComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrendingConstraintConfigComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrendingConstraintConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
