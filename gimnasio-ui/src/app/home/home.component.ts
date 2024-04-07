import { Component, OnInit } from '@angular/core';

import { LoginService } from 'app/login/login.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { Router } from '@angular/router';
import { Authority } from 'app/config/authority.constants';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  account: Account | null = null;

  constructor(private accountService: AccountService, private loginService: LoginService, private router: Router) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => (this.account = account));

    /* if not connected routes to welcome page  */
    if (this.account?.email == null) {
      console.log('this.accountService.isAuthenticated() ' + this.accountService.isAuthenticated());

      //this.router.navigate(['/welcome']);
    } else {
      console.log('this.accountService.isAuthenticated()' + this.accountService.isAuthenticated());
      this.router.navigate(['/blockchain-alp']);
      //this.router.navigate(['/address-alp']);
    }
  }

  login(): void {
    this.loginService.login();
  }
}
