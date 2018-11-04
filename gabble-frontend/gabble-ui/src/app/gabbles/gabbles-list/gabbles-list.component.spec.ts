import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GabblesListComponent } from './gabbles-list.component';

describe('GabblesListComponent', () => {
  let component: GabblesListComponent;
  let fixture: ComponentFixture<GabblesListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GabblesListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GabblesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
