import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GymDetailComponent } from './gym-detail.component';

describe('Gym Management Detail Component', () => {
  let comp: GymDetailComponent;
  let fixture: ComponentFixture<GymDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GymDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ gym: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(GymDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(GymDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load gym on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.gym).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
