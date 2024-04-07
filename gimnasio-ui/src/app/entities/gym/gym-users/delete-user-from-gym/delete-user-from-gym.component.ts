import { Component, OnInit } from '@angular/core';
import { IuserGym } from '../list/user.model';
import { UsergymService } from '../service/user.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-delete-user-from-gym',
  templateUrl: './delete-user-from-gym.component.html',
  styleUrls: ['./delete-user-from-gym.component.scss'],
})
export class DeleteUserFromGymComponent {
  user?: IuserGym;

  constructor(protected UserService: UsergymService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.UserService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
