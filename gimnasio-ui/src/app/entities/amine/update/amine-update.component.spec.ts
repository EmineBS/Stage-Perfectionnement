import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AmineUpdateComponent } from './amine-update.component';

describe('AmineUpdateComponent', () => {
  let component: AmineUpdateComponent;
  let fixture: ComponentFixture<AmineUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AmineUpdateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AmineUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
