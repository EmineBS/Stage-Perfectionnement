import { Component, OnInit } from '@angular/core';
import { IGymFeature } from '../list/feature.model';
import { IFeature } from 'app/entities/feature/feature.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-view-user-modal',
  templateUrl: './view-user-modal.component.html',
  styleUrls: ['./view-user-modal.component.scss'],
})
export class ViewUserModalComponent {
  user?: IFeature;

  constructor(private activeModal: NgbActiveModal) {}

  dismiss(): void {
    this.activeModal.dismiss();
  }
}
