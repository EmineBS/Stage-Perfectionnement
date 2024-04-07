import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { IProfile } from '../profile.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProfileFormGroup, ProfileFormService } from '../update/profile-form.service';

import { ProfileService } from '../service/profile.service';
import { Observable, finalize } from 'rxjs';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-profile-detail',
  templateUrl: './profile-detail.component.html',
})
export class ProfileDetailComponent implements OnInit {
  profile: IProfile | null = null;
  isSaving = false;
  edit = false;
  id: number;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected profileFormService: ProfileFormService,
    protected profilService: ProfileService,
    protected router: Router
  ) {}

  ngOnInit(): void {
    this.reloadCurrentRoute();
  }

  editForm: ProfileFormGroup = this.profileFormService.createProfileFormGroup();

  confirm(): void {
    this.edit = false;
    this.isSaving = true;

    const profile = this.profileFormService.getProfile(this.editForm);

    if (profile.id) {
      this.subscribeToSaveResponse(this.profilService.partialUpdate(profile));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfile>>): void {
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
    this.updateForm(this.profile!);
  }

  cancelEdit(): void {
    this.edit = false;
  }

  reloadCurrentRoute() {
    this.profilService.querySingleByAccountSession().subscribe(profile => {
      this.profile = profile.body;
    });
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(profile: IProfile): void {
    this.profile = profile;
    this.profileFormService.resetForm(this.editForm, profile);
  }

  previousState(): void {
    window.history.back();
  }
}
