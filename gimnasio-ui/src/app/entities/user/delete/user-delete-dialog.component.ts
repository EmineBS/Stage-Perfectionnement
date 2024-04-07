import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUser } from '../user.model';
import { UserService } from '../service/user.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './user-delete-dialog.component.html',
})
export class UserDeleteDialogComponent {
  user?: IUser;
  ErroMessage: string;

  constructor(protected userService: UserService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.userService.delete(id).subscribe(
      () => {
        // Success case: Item deleted
        this.activeModal.close(ITEM_DELETED_EVENT);
      },
      (error: any) => {
        // Error case: Handle the error
        console.log(error);
        this.ErroMessage = 'User cannot be removed as it is currently being used by a badge.';

        // Perform error handling actions, e.g., display an error message
        // You can also choose to re-throw the error or perform other actions based on your requirements
      }
    );
  }
}
