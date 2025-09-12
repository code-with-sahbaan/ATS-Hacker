import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CubesAnimation } from './cubes-animation';

describe('CubesAnimation', () => {
  let component: CubesAnimation;
  let fixture: ComponentFixture<CubesAnimation>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CubesAnimation]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CubesAnimation);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
