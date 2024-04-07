import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CriteriaDetailComponent } from './criteria-detail.component';

describe('Criteria Management Detail Component', () => {
  let comp: CriteriaDetailComponent;
  let fixture: ComponentFixture<CriteriaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CriteriaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ criteria: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CriteriaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CriteriaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load criteria on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.criteria).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
