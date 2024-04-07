import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICheckin } from '../checkin.model';
import { CheckinService } from '../service/checkin.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './checkin-delete-dialog.component.html',
})
export class CheckinDeleteDialogComponent {
  checkin?: ICheckin;

  constructor(protected checkinService: CheckinService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    if (this.checkin) {
      this.checkin.status = 'CANCELED';
      this.checkinService.partialUpdate(this.checkin).subscribe(() => {
        this.activeModal.close(ITEM_DELETED_EVENT);
      });
    }
  }
}
