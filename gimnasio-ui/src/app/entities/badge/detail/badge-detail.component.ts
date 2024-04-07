import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { IBadge } from '../badge.model';
import { BadgeService } from '../service/badge.service';
import { BadgeFormGroup, BadgeFormService } from '../update/badge-form.service';
import { Observable, finalize, map } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { IPack } from 'app/entities/pack/pack.model';
import { PackService } from 'app/entities/pack/service/pack.service';

@Component({
  selector: 'jhi-badge-detail',
  templateUrl: './badge-detail.component.html',
})
export class BadgeDetailComponent implements OnInit {
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
  editForm: BadgeFormGroup = this.badgeFormService.createBadgeFormGroup();

  previousState(): void {
    window.history.back();
  }

  confirm(): void {
    this.edit = false;
    this.isSaving = true;

    const badge = this.badgeFormService.getBadge(this.editForm);

    if (badge.id) {
      this.subscribeToSaveResponse(this.badgeService.partialUpdate(badge));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBadge>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => {
        this.reloadCurrentRoute();
      },
      error: () => this.onSaveError(),
    });
  }
  protected onSaveSuccess(): void {
    this.previousState();
  }

  editF(): void {
    this.edit = true;
    this.updateForm(this.badge!);
  }

  cancelEdit(): void {
    this.edit = false;
  }

  reloadCurrentRoute() {
    this.badgeService.find(this.id).subscribe(badge => {
      this.badge = badge.body;
    });
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(badge: IBadge): void {
    this.badge = badge;
    this.badgeFormService.resetForm(this.editForm, badge);
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
