import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditCarManufacturerComponent } from './edit-car-manufacturer.component';

describe('EditCarManufacturerComponent', () => {
  let component: EditCarManufacturerComponent;
  let fixture: ComponentFixture<EditCarManufacturerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditCarManufacturerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditCarManufacturerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
