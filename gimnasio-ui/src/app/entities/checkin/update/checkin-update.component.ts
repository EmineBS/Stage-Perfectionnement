import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CheckinFormService, CheckinFormGroup } from './checkin-form.service';
import { ICheckin } from '../checkin.model';
import { CheckinService } from '../service/checkin.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/service/user.service';
import { IBadge } from 'app/entities/badge/badge.model';
import { BadgeService } from 'app/entities/badge/service/badge.service';

@Component({
  selector: 'jhi-checkin-update',
  templateUrl: './checkin-update.component.html',
})
export class CheckinUpdateComponent implements OnInit {
  isSaving = false;
  checkin: ICheckin | null = null;

  usersSharedCollection: IUser[] = [];
  badgesSharedCollection: IBadge[] = [];

  editForm: CheckinFormGroup = this.checkinFormService.createCheckinFormGroup();

  constructor(
    protected checkinService: CheckinService,
    protected checkinFormService: CheckinFormService,
    protected userService: UserService,
    protected badgeService: BadgeService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareBadge = (o1: IBadge | null, o2: IBadge | null): boolean => this.badgeService.compareBadge(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkin }) => {
      this.checkin = checkin;
      if (checkin) {
        this.updateForm(checkin);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const checkin = this.checkinFormService.getCheckin(this.editForm);
    if (checkin.id !== null) {
      this.subscribeToSaveResponse(this.checkinService.update(checkin));
    } else {
      this.subscribeToSaveResponse(this.checkinService.create(checkin));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICheckin>>): void {
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

  protected updateForm(checkin: ICheckin): void {
    this.checkin = checkin;
    this.checkinFormService.resetForm(this.editForm, checkin);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, checkin.user);
    this.badgesSharedCollection = this.badgeService.addBadgeToCollectionIfMissing<IBadge>(this.badgesSharedCollection, checkin.badge);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.checkin?.user)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.badgeService
      .query()
      .pipe(map((res: HttpResponse<IBadge[]>) => res.body ?? []))
      .pipe(map((badges: IBadge[]) => this.badgeService.addBadgeToCollectionIfMissing<IBadge>(badges, this.checkin?.badge)))
      .subscribe((badges: IBadge[]) => (this.badgesSharedCollection = badges));
  }
}
