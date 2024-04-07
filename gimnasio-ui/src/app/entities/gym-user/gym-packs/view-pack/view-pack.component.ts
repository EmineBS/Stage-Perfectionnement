import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Observable, finalize } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { IPack } from 'app/entities/pack/pack.model';
import { PackFormGroup, PackFormService } from 'app/entities/pack/update/pack-form.service';
import { PackService } from 'app/entities/pack/service/pack.service';
@Component({
  selector: 'jhi-view-pack',
  templateUrl: './view-pack.component.html',
  styleUrls: ['./view-pack.component.scss'],
})
export class ViewPackComponent implements OnInit {
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

  editForm: PackFormGroup = this.packFormService.createPackFormGroup();
  
  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gym, pack }) => {
      this.pack = pack;

      this.id = pack.id;
    });
  }

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
    this.packService.find(this.id).subscribe(pack => {
      this.pack = pack.body;
    });
  }

  editF(): void {
    this.edit = true;
    this.updateForm(this.pack!);
  }

  protected updateForm(pack: IPack): void {
    this.pack = pack;
    this.packFormService.resetForm(this.editForm, pack);
  }
  
  cancelEdit(): void {
    this.edit = false;
  }
}
