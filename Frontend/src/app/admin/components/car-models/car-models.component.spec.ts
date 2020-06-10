import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CarModelsComponent } from './car-models.component';

describe('CarModelsComponent', () => {
  let component: CarModelsComponent;
  let fixture: ComponentFixture<CarModelsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CarModelsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CarModelsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
