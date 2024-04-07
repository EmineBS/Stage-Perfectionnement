import { Component, OnInit, RendererFactory2, Renderer2 } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router, ActivatedRouteSnapshot, NavigationEnd } from '@angular/router';
import { TranslateService, LangChangeEvent } from '@ngx-translate/core';
import dayjs from 'dayjs/esm';

import { AccountService } from 'app/core/auth/account.service';
import { Authority } from 'app/config/authority.constants';
import { ThemeOptions } from 'app/shared/theme-options';
import { Account } from 'app/core/auth/account.model';
import { GymService } from 'app/entities/gym-user/service/gym.service';
import { SessionStorageService } from 'ngx-webstorage';

@Component({
  selector: 'jhi-main',
  templateUrl: './main.component.html',
})
export class MainComponent implements OnInit {
  checkin = false;
  private renderer: Renderer2;
  titleHeading: string;
  account: Account | null = null;
  constructor(
    public globals: ThemeOptions,
    private accountService: AccountService,
    private titleService: Title,
    private router: Router,
    private translateService: TranslateService,
    rootRenderer: RendererFactory2,
    private gymservices: GymService,
    private sessionStorageService: SessionStorageService
  ) {
    if (!this.sessionStorageService.retrieve('gym'))
      this.gymservices.query().subscribe(data => {
        if (data.body && data.body.length > 0) {
          this.sessionStorageService.store('gym', data.body[0]);
        }
      });
    this.renderer = rootRenderer.createRenderer(document.querySelector('html'), null);
  }

  ngOnInit(): void {
    // try to log in automatically

    this.accountService.identity().subscribe({});

    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });

    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.updateTitle();
        this.titleHeading = this.titleService.getTitle();
      }
    });

    this.translateService.onLangChange.subscribe((langChangeEvent: LangChangeEvent) => {
      this.updateTitle();
      dayjs.locale(langChangeEvent.lang);
      this.renderer.setAttribute(document.querySelector('html'), 'lang', langChangeEvent.lang);
    });
  }

  private getPageTitle(routeSnapshot: ActivatedRouteSnapshot): string {
    const title: string = routeSnapshot.data['pageTitle'] ?? '';
    if (routeSnapshot.firstChild) {
      return this.getPageTitle(routeSnapshot.firstChild) || title;
    }
    return title;
  }

  private updateTitle(): void {
    let pageTitle = this.getPageTitle(this.router.routerState.snapshot.root);
    if (!pageTitle) {
      pageTitle = 'global.title';
    }
    this.translateService.get(pageTitle).subscribe(title => this.titleService.setTitle(title));

    if (this.router.url.includes('qr')) this.checkin = true;
  }
}
