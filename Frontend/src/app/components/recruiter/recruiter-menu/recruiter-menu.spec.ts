import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecruiterMenu } from './recruiter-menu';

describe('RecruiterMenu', () => {
  let component: RecruiterMenu;
  let fixture: ComponentFixture<RecruiterMenu>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RecruiterMenu]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecruiterMenu);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
