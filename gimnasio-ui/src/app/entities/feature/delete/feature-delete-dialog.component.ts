import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFeature } from '../feature.model';
import { FeatureService } from '../service/feature.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './feature-delete-dialog.component.html',
})
export class FeatureDeleteDialogComponent {
  feature?: IFeature;
  ErroMessage: string;

  constructor(protected featureService: FeatureService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.featureService.delete(id).subscribe(
      () => {
        // Success case: Item deleted
        this.activeModal.close(ITEM_DELETED_EVENT);
      },
      (error: any) => {
        // Error case: Handle the error
        console.log(error);
        this.ErroMessage = 'Feature cannot be removed as it is currently being used by a badge.';

        // Perform error handling actions, e.g., display an error message
        // You can also choose to re-throw the error or perform other actions based on your requirements
      }
    );
  }
}
