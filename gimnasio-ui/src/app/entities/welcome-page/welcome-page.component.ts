import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, Validators, FormGroup } from '@angular/forms';

import { Location } from '@angular/common';

//import interface Address
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { LoginService } from 'app/login/login.service';
import { faArrowsSpin } from '@fortawesome/free-solid-svg-icons';
import { NgbOffcanvas } from '@ng-bootstrap/ng-bootstrap';
import { GymService } from '../gym-user/service/gym.service';
import { SessionStorageService } from 'ngx-webstorage';

@Component({
  selector: 'jhi-welcome-page',
  templateUrl: './welcome-page.component.html',
  styleUrls: ['./welcome-page.component.scss'],
})
export class WelcomePageComponent implements OnInit {
  //to store address from input
  message: string;
  loading = false;
  // used to store account history
  account: Account | null = null;

  //for hidden componenent

  spin = faArrowsSpin;

  constructor(
    private router: Router,
    private accountService: AccountService,
    private loginService: LoginService,
    private location: Location,
    private gymservices: GymService,
    private sessionStorageService: SessionStorageService
  ) {}
  userData: any;
  ngOnInit(): void {
    //this.fetch().subscribe((data)=>console.log(data));
    // verify acccess
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
      if (this.account?.authorities.includes('ROLE_ADMIN')) {
        // this to redirect admin/user to the home page.
        this.router.navigate(['/admin/events']);
      } else if (this.account?.authorities.includes('ROLE_BADGE')) {
        this.router.navigate(['/tournament']);
      } else {
        this.router.navigate(['/tournament']);
      }
    });
  }

  login(): void {
    this.loginService.login();
  }
}
