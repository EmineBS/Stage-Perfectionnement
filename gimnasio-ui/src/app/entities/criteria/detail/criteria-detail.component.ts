import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { ICriteria } from '../criteria.model';
import { CriteriaFormGroup, CriteriaFormService } from '../update/criteria-form.service';
import { CriteriaService } from '../service/criteria.service';
import { Observable, finalize } from 'rxjs';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-criteria-detail',
  templateUrl: './criteria-detail.component.html',
})
export class CriteriaDetailComponent implements OnInit {
  criteria: ICriteria | null = null;
  isSaving = false;
  id: number;

  edit = false;

  constructor(
    protected activatedRoute: ActivatedRoute,

    protected router: Router,
    protected criteriaService: CriteriaService,
    protected criteriaFormService: CriteriaFormService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ criteria }) => {
      console.log("criteria object "+ JSON.stringify(criteria))
      this.criteria = criteria;
      this.id = criteria.id;
    });
  }

  editForm: CriteriaFormGroup = this.criteriaFormService.createCriteriaFormGroup();

  previousState(): void {
    window.history.back();
  }

  confirm(): void {
    this.edit = false;
    this.isSaving = true;

    const criteria = this.criteriaFormService.getCriteria(this.editForm);

    if (criteria.id) {
      this.subscribeToSaveResponse(this.criteriaService.partialUpdate(criteria));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICriteria>>): void {
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
    this.updateForm(this.criteria!);
  }

  cancelEdit(): void {
    this.edit = false;
  }

  reloadCurrentRoute() {
    this.criteriaService.find(this.id).subscribe(criteria => {
      this.criteria = criteria.body;
    });
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(criteria: ICriteria): void {
    this.criteria = criteria;
    this.criteriaFormService.resetForm(this.editForm, criteria);
  }
}
