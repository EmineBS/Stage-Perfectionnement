import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Observable, finalize } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { ICriteria } from 'app/entities/criteria/criteria.model';
import { CriteriaFormService } from 'app/entities/criteria/update/criteria-form.service';
import { CriteriaService } from 'app/entities/criteria/service/criteria.service';
@Component({
  selector: 'jhi-view-criteria',
  templateUrl: './view-criteria.component.html',
  styleUrls: ['./view-criteria.component.scss'],
})
export class ViewCriteriaComponent implements OnInit {
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
    this.activatedRoute.data.subscribe(({ gym, criteria }) => {
      this.criteria = criteria;

      this.id = criteria.id;
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

  reloadCurrentRoute() {
    this.criteriaService.find(this.id).subscribe(criteria => {
      this.criteria = criteria.body;
    });
  }
}
