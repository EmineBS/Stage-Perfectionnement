import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { UserFormService, UserFormGroup } from './user-form.service';
import { IUser } from '../user.model';
import { UserService } from '../service/user.service';

@Component({
  selector: 'jhi-user-update',
  templateUrl: './user-update.component.html',
})
export class UserUpdateComponent implements OnInit {
  isSaving = false;
  user: IUser | null = null;
  
  constructor(
    protected userService: UserService,
    protected userFormService: UserFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  editForm: UserFormGroup = this.userFormService.createUserFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ user }) => {
      this.user = user;
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const user = this.userFormService.getUser(this.editForm);
    console.log('user to save : ', user);
    if (user.id !== null) {
      //this.subscribeToSaveResponse(this.userService.update(user));
      console.log("new user cannot have an id initialy")
    } else {
      console.log('user to save 2 : ', user);
      this.subscribeToSaveResponse(this.userService.create(user));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUser>>): void {
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
}
