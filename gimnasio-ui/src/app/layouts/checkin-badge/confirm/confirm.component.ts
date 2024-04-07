import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICheckin } from 'app/entities/checkin/checkin.model';
import { CheckinService } from 'app/entities/checkin/service/checkin.service';

@Component({
  selector: 'jhi-confirm',
  templateUrl: './confirm.component.html',
  styleUrls: ['./confirm.component.scss'],
})
export class ConfirmComponent implements OnInit {
  badge?: ICheckin;

  constructor(protected checkinService: CheckinService, protected activeModal: NgbActiveModal) {}

  ngOnInit(): void {}

  cancel(): void {
    console.log('id badge' + this.badge?.status);
    if (this.badge?.status) this.badge.status = 'CANCELED';

    this.checkinService.partialUpdate(this.badge!).subscribe(data => {
      this.activeModal.close(data.body?.id);
    });
  }

  confirmCheck(): void {
    console.log('id badge' + this.badge?.status);
    if (this.badge?.status) this.badge.status = 'CONFIRMED';

    this.checkinService.checkinConfirm(this.badge!).subscribe(data => {
      this.activeModal.close(data.body?.id);
    });
  }
}
