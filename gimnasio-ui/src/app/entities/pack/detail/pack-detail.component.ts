import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { IPack } from '../pack.model';
import { PackFormGroup, PackFormService } from '../update/pack-form.service';
import { PackService } from '../service/pack.service';
import { Observable, finalize } from 'rxjs';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-pack-detail',
  templateUrl: './pack-detail.component.html',
})
export class PackDetailComponent implements OnInit {
  pack: IPack | null = null;
  isSaving = false;
  id: number;

  edit = false;

  constructor(
    protected activatedRoute: ActivatedRoute,

    protected router: Router,
    protected packService: PackService,
    protected packFormService: PackFormService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pack }) => {
      this.pack = pack;

      this.id = pack.id;
    });
  }

  editForm: PackFormGroup = this.packFormService.createPackFormGroup();

  previousState(): void {
    window.history.back();
  }

  confirm(): void {
    this.edit = false;
    this.isSaving = true;

    const pack = this.packFormService.getPack(this.editForm);

    if (pack.id) {
      this.subscribeToSaveResponse(this.packService.partialUpdate(pack));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPack>>): void {
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
    this.updateForm(this.pack!);
  }

  cancelEdit(): void {
    this.edit = false;
  }

  reloadCurrentRoute() {
    this.packService.find(this.id).subscribe(pack => {
      this.pack = pack.body;
    });
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
  }
}
