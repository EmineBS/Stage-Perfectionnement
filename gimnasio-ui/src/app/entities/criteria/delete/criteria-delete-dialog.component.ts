import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICriteria } from '../criteria.model';
import { CriteriaService } from '../service/criteria.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './criteria-delete-dialog.component.html',
})
export class CriteriaDeleteDialogComponent {
  criteria?: ICriteria;
  ErroMessage: string;

  constructor(protected criteriaService: CriteriaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.criteriaService.delete(id).subscribe(
      () => {
        // Success case: Item deleted
        this.activeModal.close(ITEM_DELETED_EVENT);
      },
      (error: any) => {
        // Error case: Handle the error
        console.log(error);
        this.ErroMessage = 'Criteria cannot be removed as it is currently being used by a badge.';

        // Perform error handling actions, e.g., display an error message
        // You can also choose to re-throw the error or perform other actions based on your requirements
      }
    );
  }
}
