import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CandidateMenu } from './candidate-menu';

describe('CandidateMenu', () => {
  let component: CandidateMenu;
  let fixture: ComponentFixture<CandidateMenu>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CandidateMenu]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CandidateMenu);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
