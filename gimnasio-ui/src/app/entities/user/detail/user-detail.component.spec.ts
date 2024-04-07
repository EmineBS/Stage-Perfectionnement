import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UserDetailComponent } from './user-detail.component';

describe('User Management Detail Component', () => {
  let comp: UserDetailComponent;
  let fixture: ComponentFixture<UserDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ user: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(UserDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(UserDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load user on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.user).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
