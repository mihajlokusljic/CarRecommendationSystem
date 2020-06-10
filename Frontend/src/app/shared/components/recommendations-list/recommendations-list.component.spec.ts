import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecommendationsListComponent } from './recommendations-list.component';

describe('RecommendationsListComponent', () => {
  let component: RecommendationsListComponent;
  let fixture: ComponentFixture<RecommendationsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecommendationsListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecommendationsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
