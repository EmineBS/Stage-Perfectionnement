import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPack } from '../pack.model';
import { PackService } from '../service/pack.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './pack-delete-dialog.component.html',
})
export class PackDeleteDialogComponent {
  pack?: IPack;
  ErroMessage: string;

  constructor(protected packService: PackService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.packService.delete(id).subscribe(
      () => {
        // Success case: Item deleted
        this.activeModal.close(ITEM_DELETED_EVENT);
      },
      (error: any) => {
        // Error case: Handle the error
        console.log(error);
        this.ErroMessage = 'Pack cannot be removed as it is currently being used by a badge.';

        // Perform error handling actions, e.g., display an error message
        // You can also choose to re-throw the error or perform other actions based on your requirements
      }
    );
  }
}
