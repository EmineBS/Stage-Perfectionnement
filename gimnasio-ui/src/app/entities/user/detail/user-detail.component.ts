import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { IUser } from '../user.model';
import { UserService } from '../service/user.service';

@Component({
  selector: 'jhi-user-detail',
  templateUrl: './user-detail.component.html',
})
export class UserDetailComponent implements OnInit {
  user: IUser | null = null;
  id: string;

  constructor(
    protected activatedRoute: ActivatedRoute,

    protected router: Router,
    protected userService: UserService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ user }) => {
      this.user = user;

      this.id = user.id;
    });
  }
  previousState(): void {
    window.history.back();
  }
}
