import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PackFormService, PackFormGroup } from './pack-form.service';
import { IPack } from '../pack.model';
import { PackService } from '../service/pack.service';
import { IBadge } from 'app/entities/badge/badge.model';
import { BadgeService } from 'app/entities/badge/service/badge.service';
import { GymService } from 'app/entities/gym/service/gym.service';
import { IGym } from 'app/entities/gym/gym.model';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'jhi-pack-update',
  templateUrl: './pack-update.component.html',
})
export class PackUpdateComponent implements OnInit {
  isSaving = false;
  pack: IPack | null = null;

  gymsSharedCollection: IGym[] = [];

  editForm: PackFormGroup = this.packFormService.createPackFormGroup();

  constructor(
    protected packService: PackService,
    protected packFormService: PackFormService,
    protected gymService: GymService,
    protected activatedRoute: ActivatedRoute
  ) {}
  compareGym = (o1: IBadge | null, o2: IBadge | null): boolean => this.gymService.compareGym(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pack }) => {
      this.pack = pack;
      if (pack) {
        this.updateForm(pack);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pack = this.packFormService.getPack(this.editForm);
    if (pack.id !== null) {
      this.subscribeToSaveResponse(this.packService.update(pack));
    } else {
      const autoConfirmControl = this.editForm.get('autoConfirm');
      const rdvEnabled = this.editForm.get('rdvEnabled');
      if (autoConfirmControl && autoConfirmControl.value === null) {
        pack.autoConfirm = false;
      }
      if (rdvEnabled && rdvEnabled.value === null) {
        pack.rdvEnabled = false;
      }
      this.subscribeToSaveResponse(this.packService.create(pack));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPack>>): void {
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

  protected updateForm(pack: IPack): void {
    this.pack = pack;
    this.packFormService.resetForm(this.editForm, pack);

    this.gymsSharedCollection = this.gymService.addGymToCollectionIfMissing<IGym>(this.gymsSharedCollection, pack.gym);
  }

  protected loadRelationshipsOptions(): void {
    this.gymService
      .query()
      .pipe(map((res: HttpResponse<IGym[]>) => res.body ?? []))
      .pipe(map((gyms: IGym[]) => this.gymService.addGymToCollectionIfMissing<IGym>(gyms, this.pack?.gym)))
      .subscribe((gyms: IGym[]) => (this.gymsSharedCollection = gyms));
  }
}
