import { Component, OnInit } from '@angular/core';
import { IGymFeature } from '../list/feature.model';
import { GymFeatureService } from '../service/gym-features.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-delete-feature-from-gym',
  templateUrl: './delete-feature-from-gym.component.html',
  styleUrls: ['./delete-feature-from-gym.component.scss'],
})
export class DeleteFeatureFromGymComponent {
  feature?: IGymFeature | null;

  constructor(protected featureService: GymFeatureService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.featureService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
