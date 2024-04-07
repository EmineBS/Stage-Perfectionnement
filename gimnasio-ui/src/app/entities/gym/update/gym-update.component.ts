import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { GymFormService, GymFormGroup } from './gym-form.service';
import { IGym } from '../gym.model';
import { GymService } from '../service/gym.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/service/user.service';
import { UsergymService } from '../gym-users/service/user.service';
import { Iuser, NewUserGym } from '../gym-users/list/user.model';

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
    protected UsergymService: UsergymService,
    protected activatedRoute: ActivatedRoute,
    protected userService: UserService,
    protected router: Router
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

  userId: string;

  save(): void {
    this.isSaving = true;
    const gym = this.gymFormService.getGym(this.editForm);
    console.log('gym object to save', gym);
    if (gym.id !== null) {
      this.subscribeToSaveResponse(this.gymService.partialUpdate(gym));
    } else {
      this.userId = gym.user?.login!;

      const badgeDesignation = gym?.badgeDesignation;
      if (!badgeDesignation) {
        gym.badgeDesignation = false;
      }

      const calendar = gym?.calendar;
      if (!calendar) {
        gym.calendar = false;
      }

      const progressMonitoring = gym?.progressMonitoring;
      if (!progressMonitoring) {
        gym.progressMonitoring = false;
      }

      this.subscribeToSaveResponse(this.gymService.create(gym));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGym>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: data => this.onSaveSuccess(data),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(data: any): void {
    const userGymItem: NewUserGym = {
      id: null,
      userId: this.userId,
      gymId: data.body.id,
    };

    this.UsergymService.create(userGymItem).subscribe();
    this.router.navigate(['/admin/gym']);
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
