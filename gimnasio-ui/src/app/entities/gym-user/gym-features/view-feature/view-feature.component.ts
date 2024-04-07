import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Observable, finalize } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { IFeature } from 'app/entities/feature/feature.model';
import { FeatureService } from 'app/entities/feature/service/feature.service';
@Component({
  selector: 'jhi-view-feature',
  templateUrl: './view-feature.component.html',
  styleUrls: ['./view-feature.component.scss'],
})
export class ViewFeatureComponent implements OnInit {
  feature: IFeature | null = null;
  isSaving = false;
  id: number;
  
  constructor(
    protected activatedRoute: ActivatedRoute,

    protected router: Router,
    protected featureService: FeatureService,
  ) {}

  
  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feature }) => {
      console.log("feature is ")
      this.feature = feature;
      console.log("feature is "+ JSON.stringify(feature))
    });
  }

  previousState(): void {
    window.history.back();
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFeature>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => {
        this.reloadCurrentRoute();
      },
      error: () => this.onSaveError(),
    });
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }
  
  protected onSaveError(): void {
    // Api for inheritance.
  }
  
  reloadCurrentRoute() {
    this.featureService.find(this.id).subscribe(feature => { 
      this.feature = feature.body;
    });
  }

}
