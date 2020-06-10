import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCarManufacturerComponent } from './add-car-manufacturer.component';

describe('AddCarManufacturerComponent', () => {
  let component: AddCarManufacturerComponent;
  let fixture: ComponentFixture<AddCarManufacturerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddCarManufacturerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddCarManufacturerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
