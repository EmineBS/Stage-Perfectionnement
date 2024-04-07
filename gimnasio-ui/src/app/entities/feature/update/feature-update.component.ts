import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { FeatureFormService, FeatureFormGroup } from './feature-form.service';
import { IFeature } from '../feature.model';
import { FeatureService } from '../service/feature.service';
import { IBadge } from 'app/entities/badge/badge.model';
import { BadgeService } from 'app/entities/badge/service/badge.service';
import { GymService } from 'app/entities/gym/service/gym.service';
import { IGym } from 'app/entities/gym/gym.model';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'jhi-feature-update',
  templateUrl: './feature-update.component.html',
})
export class FeatureUpdateComponent implements OnInit {
  isSaving = false;
  feature: IFeature | null = null;

  gymsSharedCollection: IGym[] = [];

  editForm: FeatureFormGroup = this.featureFormService.createFeatureFormGroup();

  constructor(
    protected featureService: FeatureService,
    protected featureFormService: FeatureFormService,
    protected gymService: GymService,
    protected activatedRoute: ActivatedRoute
  ) {}
  compareGym = (o1: IBadge | null, o2: IBadge | null): boolean => this.gymService.compareGym(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feature }) => {
      this.feature = feature;
      if (feature) {
        this.updateForm(feature);
      }

      //this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const feature = this.featureFormService.getFeature(this.editForm);
    console.log('feature to save : ', feature);
    if (feature.id !== null) {
      this.subscribeToSaveResponse(this.featureService.update(feature));
    } else {
      const enabled = this.editForm.get('enabled');
      if (enabled || enabled!.value === null) {
        feature.enabled = false;
      }
      console.log('feature to save 2 : ', feature);
      this.subscribeToSaveResponse(this.featureService.create(feature));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFeature>>): void {
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

  protected updateForm(feature: IFeature): void {
    this.feature = feature;
    this.featureFormService.resetForm(this.editForm, feature);

    this.gymsSharedCollection = this.gymService.addGymToCollectionIfMissing<IGym>(this.gymsSharedCollection, feature);
  }

  // protected loadRelationshipsOptions(): void {
  //   this.gymService
  //     .query()
  //     .pipe(map((res: HttpResponse<IGym[]>) => res.body ?? []))
  //     .pipe(map((gyms: IGym[]) => this.gymService.addGymToCollectionIfMissing<IGym>(gyms, this.feature?.gym)))
  //     .subscribe((gyms: IGym[]) => (this.gymsSharedCollection = gyms));
  // }
}
