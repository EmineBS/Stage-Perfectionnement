import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { IGym } from 'app/entities/gym/gym.model';
import { GymService } from 'app/entities/gym/service/gym.service';
import { ThemeOptions } from 'app/shared/theme-options';
import { SessionStorageService } from 'ngx-webstorage';

@Component({
  selector: 'jhi-sidebar-header',
  templateUrl: './sidebar-header.component.html',
  styleUrls: ['./sidebar-header.component.scss'],
})
export class SidebarHeaderComponent implements OnInit {
  constructor(public globals: ThemeOptions, private sessionStorageService: SessionStorageService, private gymService: GymService,private accountService: AccountService) {}
  gymName : string | null | undefined = 'Platform';
  account: Account | null = null;
  ngOnInit(): void {
    //if user gym
    const storedData = this.sessionStorageService.retrieve('game');
    if (storedData) this.gymName = storedData.name;
    //if user badge
    // gym service by badgesession
    /**this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
      if (this.account?.authorities.includes('ROLE_BADGE')) {
        this.gymService.findBySession()
        .subscribe(
          (response: HttpResponse<IGym>): void => {
            if(response.body){
              this.gymName = response.body!.name
            }
          },
          (error) => {
            console.error(error);
        }
      );
      }
    });**/

  }

  toggleSidebarCollapse() {
    this.globals.toggleSidebar = !this.globals.toggleSidebar;
  }

  toggleSidebarMobileOpen() {
    this.globals.toggleSidebarMobile = !this.globals.toggleSidebarMobile;
    this.globals.toggleSidebar = false;
  }
}
