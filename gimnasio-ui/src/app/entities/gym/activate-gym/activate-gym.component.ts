import { Component, OnInit } from '@angular/core';
import { GymService } from '../service/gym.service';
import { IGym } from '../gym.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  selector: 'jhi-activate-gym',
  templateUrl: './activate-gym.component.html',
  styleUrls: ['./activate-gym.component.scss'],
})
export class ActivateGymComponent implements OnInit {
  gym?: IGym;
  constructor(protected gymService: GymService, protected activeModal: NgbActiveModal) {}

  ngOnInit(): void {}
  cancel(): void {
    this.activeModal.dismiss();
  }
  confirmDelete(id: number): void {
    if (this.gym) {
      this.gym.status = 'ACTIVE';
      this.gymService.partialUpdate(this.gym).subscribe(() => {
        this.activeModal.close(ITEM_DELETED_EVENT);
      });
    }
  }
}
