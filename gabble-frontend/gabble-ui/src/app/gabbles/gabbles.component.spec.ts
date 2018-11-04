import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GabblesComponent } from './gabbles.component';

describe('GabblesComponent', () => {
  let component: GabblesComponent;
  let fixture: ComponentFixture<GabblesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GabblesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GabblesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
