import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AjoutAmineComponent } from './ajout.component';

describe('AjoutComponent', () => {
  let component: AjoutAmineComponent;
  let fixture: ComponentFixture<AjoutAmineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AjoutAmineComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AjoutAmineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
