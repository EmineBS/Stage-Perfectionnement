import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { Iuser } from 'app/entities/gym/gym-users/list/user.model';
import { startOfDay, endOfDay, subDays, addDays, endOfMonth, isSameDay, isSameMonth, addHours } from 'date-fns';
import { VisitService } from '../../visit/service/visit.service';
import { NewVisit } from '../../visit/visit.model';

@Component({
  selector: 'jhi-modal-create-visit',
  templateUrl: './modal-create-visit.component.html',
  styleUrls: ['./modal-create-visit.component.scss'],
})
export class ModalCreateVisitComponent {
  user?: Iuser;
  DateVisit: Date;
  DateEnds: Date;
  VisitDay: Date;
  PackName: String = '';
  Title = '';

  constructor(private activeModal: NgbActiveModal, private visitService: VisitService) {}

  AddVisit(): void {
    this.activeModal.close('CONFIRMED');
  }

  dismiss(): void {
    this.activeModal.dismiss();
  }
}
