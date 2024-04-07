import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbOffcanvas } from '@ng-bootstrap/ng-bootstrap';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { IBadge } from 'app/entities/badge/badge.model';
import { ICheckin } from 'app/entities/checkin/checkin.model';
import { LoginService } from 'app/login/login.service';

@Component({
  selector: 'jhi-checkin-result',
  templateUrl: './checkin-result.component.html',
  styleUrls: ['./checkin-result.component.scss'],
})
export class CheckinResultComponent implements OnInit {
  closeResult: string;
  res: ICheckin;
  badge: ICheckin;
  exception: string;
  color = 'success';
  message = 'CHECK-IN SUCCEED';
  reason = 'Active Pack : ';
  pack = '';
  account: Account | null = null;

  constructor(
    private offcanvasService: NgbOffcanvas,
    protected activatedRoute: ActivatedRoute,
    private loginService: LoginService,
    private router: Router,
    protected accountService: AccountService
  ) {}

  ngOnInit(): void {
    //console.log("badge status is : "+this.badge.status);

    if (this.exception) {
      this.message = 'CHECK-IN FAILED !';
      this.reason = this.exception;
      this.color = 'danger';
    } else if (this.badge.status === 'CONFIRMED') {
      this.pack = this.badge.packName!;
      this.reason =
        'Upon validating the provided badge UID " ' +
        this.badge.badgeUid +
        '" associated To ' +
        this.pack +
        'which includes a total of ' +
        this.badge.packWorkoutSessions +
        ' sessions, there are currently ' +
        this.badge.checkinLeft +
        ' sessions remaining.';
    } else {
      this.color = 'danger';
      this.message = 'CHECK-IN FAILED';
      if (this.badge.status === 'REFUSED') {
        this.pack = this.badge.packName!;
        this.reason = 'Regrettably, the badge with UID "' + this.badge.badgeUid + '" associated with ' + this.pack + ' has expired. !';
      } else
        this.reason =
          'Unfortunately, the badge with UID "' +
          this.badge.badgeUid +
          '" associated with ' +
          this.pack +
          ' has been canceled by the administrator.';
    }
  }

  cancel(): void {
    this.offcanvasService.dismiss();
  }

  previousState(): void {
    this.router.navigate(['./']);
  }

  login(): void {
    this.loginService.login();
  }
}
