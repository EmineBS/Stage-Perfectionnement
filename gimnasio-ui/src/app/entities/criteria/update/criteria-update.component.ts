import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CriteriaFormService, CriteriaFormGroup } from './criteria-form.service';
import { ICriteria } from '../criteria.model';
import { CriteriaService } from '../service/criteria.service';
import { IBadge } from 'app/entities/badge/badge.model';
import { BadgeService } from 'app/entities/badge/service/badge.service';
import { GymService } from 'app/entities/gym/service/gym.service';
import { IGym } from 'app/entities/gym/gym.model';
import { FormControl } from '@angular/forms';
import { CRITERIA_DISPLAY } from 'app/entities/enumerations/criteriaDisplay';

@Component({
  selector: 'jhi-criteria-update',
  templateUrl: './criteria-update.component.html',
})
export class CriteriaUpdateComponent implements OnInit {
  isSaving = false;
  criteria: ICriteria | null = null;

  gymsSharedCollection: IGym[] = [];
  criteriaDisplay = Object.values(CRITERIA_DISPLAY);

  editForm: CriteriaFormGroup = this.criteriaFormService.createCriteriaFormGroup();

  constructor(
    protected criteriaService: CriteriaService,
    protected criteriaFormService: CriteriaFormService,
    protected gymService: GymService,
    protected activatedRoute: ActivatedRoute
  ) {}
  compareGym = (o1: IBadge | null, o2: IBadge | null): boolean => this.gymService.compareGym(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ criteria }) => {
      this.criteria = criteria;
      if (criteria) {
        this.updateForm(criteria);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const criteria = this.criteriaFormService.getCriteria(this.editForm);
    console.log('criteria to save : ', criteria);
    if (criteria.id !== null) {
      this.subscribeToSaveResponse(this.criteriaService.update(criteria));
    } else {
      const enabled = this.editForm.get('enabled');
      if (enabled || enabled!.value === null) {
        criteria.enabled = false;
      }
      criteria.gymId = criteria.gym?.id;
      console.log('criteria to save 2 : ', criteria);
      this.subscribeToSaveResponse(this.criteriaService.create(criteria));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICriteria>>): void {
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

  protected updateForm(criteria: ICriteria): void {
    this.criteria = criteria;
    this.criteriaFormService.resetForm(this.editForm, criteria);

    this.gymsSharedCollection = this.gymService.addGymToCollectionIfMissing<IGym>(this.gymsSharedCollection, criteria.gym);
  }

  protected loadRelationshipsOptions(): void {
    this.gymService
      .query()
      .pipe(map((res: HttpResponse<IGym[]>) => res.body ?? []))
      .pipe(map((gyms: IGym[]) => this.gymService.addGymToCollectionIfMissing<IGym>(gyms, this.criteria?.gym)))
      .subscribe((gyms: IGym[]) => (this.gymsSharedCollection = gyms));
  }
}
