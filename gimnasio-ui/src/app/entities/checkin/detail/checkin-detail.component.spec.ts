import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CheckinDetailComponent } from './checkin-detail.component';

describe('Checkin Management Detail Component', () => {
  let comp: CheckinDetailComponent;
  let fixture: ComponentFixture<CheckinDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CheckinDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ checkin: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CheckinDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CheckinDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load checkin on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.checkin).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
