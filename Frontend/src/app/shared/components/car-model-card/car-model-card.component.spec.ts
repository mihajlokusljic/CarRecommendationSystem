import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CarModelCardComponent } from './car-model-card.component';

describe('CarModelCardComponent', () => {
  let component: CarModelCardComponent;
  let fixture: ComponentFixture<CarModelCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CarModelCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CarModelCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
