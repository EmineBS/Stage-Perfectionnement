import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FeatureDetailComponent } from './feature-detail.component';

describe('Feature Management Detail Component', () => {
  let comp: FeatureDetailComponent;
  let fixture: ComponentFixture<FeatureDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FeatureDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ feature: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FeatureDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FeatureDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load feature on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.feature).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
