import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IBadge } from 'app/entities/badge/badge.model';
import { BadgeService } from 'app/entities/badge/service/badge.service';
import { STATUS } from 'app/entities/enumerations/status.model';
import { GymService } from 'app/entities/gym-user/service/gym.service';

@Component({
  selector: 'jhi-deactivate-badge',
  templateUrl: './deactivate-badge.component.html',
  styleUrls: ['./deactivate-badge.component.scss'],
})
export class DeactivateBadgeComponent {
  badge?: IBadge;

  constructor(protected badgeService: BadgeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    if (this.badge) {
      this.badge.status = STATUS.NOTACTIVATED;
      this.badgeService.partialUpdate(this.badge).subscribe(() => {
        this.activeModal.close(ITEM_DELETED_EVENT);
      });
    }
  }
}
