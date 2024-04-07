import { Component, OnInit } from '@angular/core';
import { Observable, finalize, map } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { IPack } from 'app/entities/pack/pack.model';
import { PackService } from 'app/entities/pack/service/pack.service';
import { IBadge } from 'app/entities/badge/badge.model';
import { ActivatedRoute, Router } from '@angular/router';
import { BadgeService } from 'app/entities/badge/service/badge.service';
import { BadgeFormService } from 'app/entities/badge/update/badge-form.service';
@Component({
  selector: 'jhi-view-badge',
  templateUrl: './view-badge.component.html',
  styleUrls: ['./view-badge.component.scss'],
})
export class ViewBadgeComponent implements OnInit {
  badge: IBadge | null = null;

  isSaving = false;
  id: number;

  packsSharedCollection: IPack[] = [];
  edit = false;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected badgeService: BadgeService,
    protected badgeFormService: BadgeFormService,

    protected packService: PackService
  ) {}

  comparePack = (o1: IPack | null, o2: IPack | null): boolean => this.packService.comparePack(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ badge }) => {
      this.badge = badge;

      this.id = badge.id;
    });
  }

  previousState(): void {
    window.history.back();
  }

  confirm(): void {
    this.edit = false;
    this.isSaving = true;
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  cancelEdit(): void {
    this.edit = false;
  }

  reloadCurrentRoute() {
    this.badgeService.find(this.id).subscribe(badge => {
      this.badge = badge.body;
    });
  }

  getBadgeClass(statusState: string): string {
    if (statusState === 'inactif') {
      return 'bg-danger';
    }
    return 'bg-success';
  }

  protected loadRelationshipsOptions(): void {
    this.packService
      .query()
      .pipe(map((res: HttpResponse<IPack[]>) => res.body ?? []))
      .pipe(map((packs: IPack[]) => this.packService.addPackToCollectionIfMissing<IPack>(packs, this.badge?.packId)))
      .subscribe((packs: IPack[]) => (this.packsSharedCollection = packs));
  }
}
