import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { IGym } from '../gym.model';
import { GymFormGroup, GymFormService } from '../update/gym-form.service';
import { GymService } from '../service/gym.service';
import { Observable, finalize, map } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { UserService } from 'app/entities/user/service/user.service';
import { IUser } from 'app/entities/user/user.model';

@Component({
  selector: 'jhi-gym-detail',
  templateUrl: './gym-detail.component.html',
})
export class GymDetailComponent implements OnInit {
  gym: IGym | null = null;

  isSaving = false;
  id: number;
  usersSharedCollection: IUser[] = [];

  edit = false;
  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected gymFormService: GymFormService,
    protected gymService: GymService,
    protected router: Router,
    protected userService: UserService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gym }) => {
      this.gym = gym;

      this.id = gym.id;
    });
  }

  editForm: GymFormGroup = this.gymFormService.createGymFormGroup();

  previousState(): void {
    window.history.back();
  }

  confirm(): void {
    this.edit = false;
    this.isSaving = true;

    const gym = this.gymFormService.getGym(this.editForm);

    if (gym.id) {
      this.subscribeToSaveResponse(this.gymService.partialUpdate(gym));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGym>>): void {
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
    this.loadRelationshipsOptions();
    this.edit = true;
    this.updateForm(this.gym!);
  }

  cancelEdit(): void {
    this.edit = false;
  }

  reloadCurrentRoute() {
    this.gymService.find(this.id).subscribe(gym => {
      this.gym = gym.body;
    });
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
      .queryAdmin()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.gym?.user)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }
}
