import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CarManufacturersComponent } from './car-manufacturers.component';

describe('CarManufacturersComponent', () => {
  let component: CarManufacturersComponent;
  let fixture: ComponentFixture<CarManufacturersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CarManufacturersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CarManufacturersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
