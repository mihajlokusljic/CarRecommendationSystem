import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditCarModelComponent } from './edit-car-model.component';

describe('EditCarModelComponent', () => {
  let component: EditCarModelComponent;
  let fixture: ComponentFixture<EditCarModelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditCarModelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditCarModelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
