import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProgressComponent } from './progress.component';

describe('Profile Management Detail Component', () => {
  let comp: ProgressComponent;
  let fixture: ComponentFixture<ProgressComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProgressComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ profile: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ProgressComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProgressComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load profile on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.progresss).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
