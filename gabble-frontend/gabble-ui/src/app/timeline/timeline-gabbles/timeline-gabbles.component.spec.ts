import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TimelineGabblesComponent } from './timeline-gabbles.component';

describe('TimelineGabblesComponent', () => {
  let component: TimelineGabblesComponent;
  let fixture: ComponentFixture<TimelineGabblesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TimelineGabblesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TimelineGabblesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
