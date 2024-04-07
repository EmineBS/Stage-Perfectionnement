import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IRelatedTx } from 'app/entities/transaction/transaction.model';

@Component({
  selector: 'jhi-related-transaction-modal',
  templateUrl: './related-transaction-modal.component.html',
  styleUrls: ['./related-transaction-modal.component.scss'],
})
export class RelatedTransactionModalComponent {
  transaction?: IRelatedTx;

  constructor(private activeModal: NgbActiveModal) {}

  dismiss(): void {
    this.activeModal.dismiss();
  }

  getBadgeClass(statusState: string): string {
    if (statusState === 'SUSPECTED') {
      return 'bg-danger';
    }
    return 'bg-success';
  }
}
