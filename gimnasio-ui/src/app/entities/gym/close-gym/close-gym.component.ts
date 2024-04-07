import { Component, OnInit } from '@angular/core';
import { GymService } from '../service/gym.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IGym } from '../gym.model';

@Component({
  selector: 'jhi-close-gym',
  templateUrl: './close-gym.component.html',
  styleUrls: ['./close-gym.component.scss'],
})
export class CloseGymComponent {
  gym?: IGym;

  constructor(protected gymService: GymService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    if (this.gym) {
      this.gym.status = 'INACTIVE';
      this.gymService.partialUpdate(this.gym).subscribe(() => {
        this.activeModal.close(ITEM_DELETED_EVENT);
      });
    }
  }
}
