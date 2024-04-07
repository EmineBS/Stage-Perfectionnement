import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PackDetailComponent } from './pack-detail.component';

describe('Pack Management Detail Component', () => {
  let comp: PackDetailComponent;
  let fixture: ComponentFixture<PackDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PackDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ pack: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PackDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PackDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load pack on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.pack).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
