import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { BadgeFormService, BadgeFormGroup } from './badge-form.service';
import { IBadge } from '../badge.model';
import { BadgeService } from '../service/badge.service';
import { IPack } from 'app/entities/pack/pack.model';
import { PackService } from 'app/entities/pack/service/pack.service';
import { STATUS } from 'app/entities/enumerations/status.model';

@Component({
  selector: 'jhi-badge-update',
  templateUrl: './badge-update.component.html',
})
export class BadgeUpdateComponent implements OnInit {
  isSaving = false;
  badge: IBadge | null = null;
  sTATUSValues = Object.keys(STATUS);

  packsSharedCollection: IPack[] = [];

  editForm: BadgeFormGroup = this.badgeFormService.createBadgeFormGroup();

  constructor(
    protected badgeService: BadgeService,
    protected badgeFormService: BadgeFormService,
    protected packService: PackService,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePack = (o1: IPack | null, o2: IPack | null): boolean => this.packService.comparePack(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ badge }) => {
      this.badge = badge;
      if (badge) {
        this.updateForm(badge);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const badge = this.badgeFormService.getBadge(this.editForm);
    if (badge.id !== null) {
      this.subscribeToSaveResponse(this.badgeService.partialUpdate(badge));
    } else {
      this.subscribeToSaveResponse(this.badgeService.create(badge));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBadge>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
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

    this.packsSharedCollection = this.packService.addPackToCollectionIfMissing<IPack>(this.packsSharedCollection, badge.packId);
  }

  protected loadRelationshipsOptions(): void {
    this.packService
      .query()
      .pipe(map((res: HttpResponse<IPack[]>) => res.body ?? []))
      .pipe(map((packs: IPack[]) => this.packService.addPackToCollectionIfMissing<IPack>(packs, this.badge?.packId)))
      .subscribe((packs: IPack[]) => (this.packsSharedCollection = packs));
  }
}
