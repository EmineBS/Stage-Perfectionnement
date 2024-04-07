import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IBadge } from 'app/entities/badge/badge.model';
import { BadgeService } from 'app/entities/badge/service/badge.service';
import { VisitService } from 'app/entities/gym-user/visit/service/visit.service';
import { map } from 'rxjs';

@Component({
  selector: 'jhi-add-user-visit',
  templateUrl: './add-user-visit.component.html',
  styleUrls: ['./add-user-visit.component.scss'],
})
export class AddUserVisitComponent implements OnInit {
  badgesSharedCollection: IBadge[];
  badgeToUpdate: IBadge;
  DateVisit: Date;
  DateEnds: Date;
  VisitDay: Date;
  Title = '';
  idgym: number;

  constructor(private activeModal: NgbActiveModal, private visitService: VisitService, private badgeService: BadgeService) {}
  ngOnInit(): void {
    this.loadRelationshipsOptions();
  }

  compareBadge = (o1: IBadge | null, o2: IBadge | null): boolean => this.badgeService.compareBadge(o1, o2);

  protected loadRelationshipsOptions(): void {
    this.badgeService
      .findBadgefromGym(this.idgym)
      .pipe(map((res: HttpResponse<IBadge[]>) => res.body ?? []))
      .pipe(map((packs: IBadge[]) => this.badgeService.addBadgeToCollectionIfMissing<IBadge>(packs, this.badgeToUpdate?.packId)))
      .subscribe((packs: IBadge[]) => (this.badgesSharedCollection = packs));
  }

  AddVisit(): void {
    this.activeModal.close('CONFIRMED');
  }

  dismiss(): void {
    this.activeModal.dismiss();
  }
}
