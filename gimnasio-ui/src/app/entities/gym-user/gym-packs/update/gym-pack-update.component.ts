import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { GymPackFormService, PackFormGroup } from './gym-pack-form.service';
import { IPack } from 'app/entities/pack/pack.model';
import { PackService } from 'app/entities/pack/service/pack.service';
import { IBadge } from 'app/entities/badge/badge.model';
import { BadgeService } from 'app/entities/badge/service/badge.service';
import { GymService } from 'app/entities/gym/service/gym.service';
import { IGym } from 'app/entities/gym/gym.model';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'jhi-gym-pack-update',
  templateUrl: './gym-pack-update.component.html',
})
export class GymPackUpdateComponent implements OnInit {
  isSaving = false;
  pack: IPack | null = null;

  gym : IGym;

  gymsSharedCollection: IGym[] = [];

  editForm: PackFormGroup = this.GymPackFormService.createPackFormGroup();

  constructor(
    protected packService: PackService,
    protected GymPackFormService: GymPackFormService,
    protected gymService: GymService,
    protected activatedRoute: ActivatedRoute
  ) {}
  compareGym = (o1: IBadge | null, o2: IBadge | null): boolean => this.gymService.compareGym(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pack, gym }) => {

      this.gym = gym;

      this.pack = pack;
      if (pack) {
        this.updateForm(pack);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pack = this.GymPackFormService.getPack(this.editForm);
    if (pack.id !== null) {
      this.subscribeToSaveResponse(this.packService.update(pack));
    } else {
      const autoConfirmControl = this.editForm.get('autoConfirm');
      const rdvEnabled = this.editForm.get('rdvEnabled');
      const gym = this.editForm.get('gym');
      if (autoConfirmControl && autoConfirmControl.value === null) {
        pack.autoConfirm = false;
      }
      if (rdvEnabled && rdvEnabled.value === null) {
        pack.rdvEnabled = false;
      }
      pack.gym = this.gym
        console.log("pack recieved", JSON.stringify(pack))
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
    this.GymPackFormService.resetForm(this.editForm, pack);

    this.gymsSharedCollection = this.gymService.addGymToCollectionIfMissing<IGym>(this.gymsSharedCollection, pack.gym);
  }
}
