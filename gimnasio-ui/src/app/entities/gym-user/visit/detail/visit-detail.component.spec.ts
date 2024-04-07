import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VisitDetailComponent } from './visit-detail.component';

describe('Visit Management Detail Component', () => {
  let comp: VisitDetailComponent;
  let fixture: ComponentFixture<VisitDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VisitDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ visit: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(VisitDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(VisitDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load visit on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.visit).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
