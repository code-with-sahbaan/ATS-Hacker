import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AiInterview } from './ai-interview';

describe('AiInterview', () => {
  let component: AiInterview;
  let fixture: ComponentFixture<AiInterview>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AiInterview]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AiInterview);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
