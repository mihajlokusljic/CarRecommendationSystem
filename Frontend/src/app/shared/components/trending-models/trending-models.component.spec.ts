import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrendingModelsComponent } from './trending-models.component';

describe('TrendingModelsComponent', () => {
  let component: TrendingModelsComponent;
  let fixture: ComponentFixture<TrendingModelsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrendingModelsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrendingModelsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
