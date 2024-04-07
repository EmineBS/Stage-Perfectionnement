import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { IFeature } from '../feature.model';
import { FeatureFormGroup, FeatureFormService } from '../update/feature-form.service';
import { FeatureService } from '../service/feature.service';
import { Observable, finalize } from 'rxjs';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-feature-detail',
  templateUrl: './feature-detail.component.html',
})
export class FeatureDetailComponent implements OnInit {
  feature: IFeature | null = null;
  isSaving = false;
  id: number;

  edit = false;

  constructor(
    protected activatedRoute: ActivatedRoute,

    protected router: Router,
    protected featureService: FeatureService,
    protected featureFormService: FeatureFormService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feature }) => {
      console.log("feature object "+ JSON.stringify(feature))
      this.feature = feature;
      this.id = feature.id;
    });
  }

  editForm: FeatureFormGroup = this.featureFormService.createFeatureFormGroup();

  previousState(): void {
    window.history.back();
  }

  confirm(): void {
    this.edit = false;
    this.isSaving = true;

    const feature = this.featureFormService.getFeature(this.editForm);

    if (feature.id) {
      this.subscribeToSaveResponse(this.featureService.partialUpdate(feature));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFeature>>): void {
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
    this.updateForm(this.feature!);
  }

  cancelEdit(): void {
    this.edit = false;
  }

  reloadCurrentRoute() {
    this.featureService.find(this.id).subscribe(feature => {
      this.feature = feature.body;
    });
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
  }
}
