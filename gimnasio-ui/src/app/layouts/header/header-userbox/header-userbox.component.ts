import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { faBell } from '@fortawesome/free-solid-svg-icons';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';

@Component({
  selector: 'jhi-header-userbox',
  templateUrl: './header-userbox.component.html',
  styleUrls: ['./header-userbox.component.scss'],
})
export class HeaderUserboxComponent implements OnInit {
  account: Account | null = null;
  @Input() Login: string;
  @Input() Email: string;
  @Input() accStatus: string;

  constructor(private loginService: LoginService, private accountService: AccountService, private router: Router) {}

  ngOnInit(): void {}

  logout(): void {
    this.loginService.logout();
    this.router.navigate(['']);
  }
}
