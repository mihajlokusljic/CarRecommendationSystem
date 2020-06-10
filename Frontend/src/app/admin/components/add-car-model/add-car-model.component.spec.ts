import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCarModelComponent } from './add-car-model.component';

describe('AddCarModelComponent', () => {
  let component: AddCarModelComponent;
  let fixture: ComponentFixture<AddCarModelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddCarModelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddCarModelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
