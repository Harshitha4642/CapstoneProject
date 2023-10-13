import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CallFormComponent } from './call-form.component';

describe('CallFormComponent', () => {
  let component: CallFormComponent;
  let fixture: ComponentFixture<CallFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CallFormComponent]
    });
    fixture = TestBed.createComponent(CallFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
