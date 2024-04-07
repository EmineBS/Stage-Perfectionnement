import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { GymFormService, GymFormGroup } from './gym-form.service';
import { IGym } from '../gym.model';
import { GymService } from '../service/gym.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/service/user.service';

@Component({
  selector: 'jhi-gym-update',
  templateUrl: './gym-update.component.html',
})
export class GymUpdateComponent implements OnInit {
  isSaving = false;
  gym: IGym | null = null;

  usersSharedCollection: IUser[] = [];

  constructor(
    protected gymService: GymService,
    protected gymFormService: GymFormService,
    protected activatedRoute: ActivatedRoute,
    protected userService: UserService
  ) {}

  editForm: GymFormGroup = this.gymFormService.createGymFormGroup();

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gym }) => {
      this.gym = gym;
      if (gym) {
        this.updateForm(gym);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gym = this.gymFormService.getGym(this.editForm);
    if (gym.id !== null) {
      this.subscribeToSaveResponse(this.gymService.partialUpdate(gym));
    } else {
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGym>>): void {
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

  protected updateForm(gym: IGym): void {
    this.gym = gym;
    this.gymFormService.resetForm(this.editForm, gym);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.gym?.user)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }
}
