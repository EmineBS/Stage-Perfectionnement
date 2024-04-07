import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RelatedTransactionModalComponent } from './related-transaction-modal.component';

describe('RelatedTransactionModalComponent', () => {
  let component: RelatedTransactionModalComponent;
  let fixture: ComponentFixture<RelatedTransactionModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RelatedTransactionModalComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(RelatedTransactionModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
