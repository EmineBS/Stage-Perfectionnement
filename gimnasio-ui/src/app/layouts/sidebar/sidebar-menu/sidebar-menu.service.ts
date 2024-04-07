import { Injectable, OnInit } from '@angular/core';
import { ASC, DESC } from 'app/config/navigation.constants';
import { PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { AccountService } from 'app/core/auth/account.service';
import { GymService } from 'app/entities/gym-user/service/gym.service';
import { IGym } from 'app/entities/gym-user/gym.model';
import { EntityArrayResponseType } from 'app/entities/gym-user/service/gym.service';
import { combineLatest, filter, Observable, switchMap, tap } from 'rxjs';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { HttpHeaders } from '@angular/common/http';
import { ThemeOptions } from 'app/shared/theme-options';
import { SessionStorageService } from 'ngx-webstorage';
import { LangChangeEvent, TranslateService } from '@ngx-translate/core';

interface MenuItem {
  title: string;
  type: string;
  badge?: {
    class: string;
    text: string;
  };
  link?: string;
  active?: boolean;
  icon?: string;
  submenus?: MenuItem[];
}

@Injectable({
  providedIn: 'root',
})
export class SidebarMenuService {
  adminMenu: MenuItem[] = [
    {
      title: 'Admin Menu',
      type: 'header',
    },

    /**{
      title: 'Gyms',
      type: 'dropdown',
      icon: 'dumbbell',
      link: 'admin/gym',
      submenus: [
        {
          title: 'Add Gym',
          type: 'simple',
          link: 'admin/gym/new',
          icon: 'list',
        },
      ],
    },
    {
      title: 'Feature',
      type: 'dropdown',
      icon: 'dumbbell',
      link: 'admin/feature',
      submenus: [
        {
          title: 'Add Feature',
          type: 'simple',
          link: 'admin/feature/new',
          icon: 'list',
        },
      ],
    },
    {
      title: 'Packs',
      type: 'dropdown',
      icon: 'hand-sparkles',
      link: '/admin/pack',
      submenus: [
        {
          title: 'Add Pack',
          type: 'simple',
          link: '/admin/pack/new',
          icon: 'list',
        },
      ],
    },
    {
      title: 'Criteria',
      type: 'dropdown',
      icon: 'circle-arrow-up',
      link: '/admin/criteria',
      submenus: [
        {
          title: 'Add Criteria',
          type: 'simple',
          link: '/admin/criteria/new',
          icon: 'list',
        },
      ],
    },
    {
      title: 'User',
      type: 'dropdown',
      icon: 'user',
      link: '/admin/user',
      submenus: [
        {
          title: 'Add User',
          type: 'simple',
          link: '/admin/user/new',
          icon: 'list',
        },
      ],
    },
    {
      title: 'Amine',
      type: 'dropdown',
      link: '/admin/amine',
      icon: 'user',
      submenus: [
        {
          title: 'Add Amine',
          type: 'simple',
          link: '/admin/amine/new',
          icon: 'list',
        },
      ],
    },
    {
      title: 'Badges',
      type: 'simple',
      link: '/admin/badge',
      icon: 'id-badge',
    },
    {
      title: 'Configuration',
      type: 'simple',
      link: '/admin/configuration',
      icon: 'cogs',
    },
    {
      title: 'Gateway',
      type: 'simple',
      link: '/admin/gateway',
      icon: 'road',
    },
    {
      title: 'Metrics',
      type: 'simple',
      link: '/admin/metrics',
      icon: 'search',
    },
    {
      title: 'Logs',
      type: 'simple',
      link: '/admin/logs',
      icon: 'database',
    },
    {
      title: 'Health',
      type: 'simple',
      link: '/admin/health',
      icon: 'heart',
    },**/
    {
      title: 'Indies',
      type: 'simple',
      link: '/admin/indies',
      icon: 'id-badge',
    },
    {
      title: 'Users',
      type: 'simple',
      link: '/admin/users',
      icon: 'user',
    },
    {
      title: 'DOCS',
      type: 'simple',
      link: '/admin/docs',
      icon: 'book',
    },
  ];

  menus: MenuItem[];
  pack: MenuItem;
  feature: MenuItem;
  badge: MenuItem;
  criteria: MenuItem;
  progress: MenuItem;
  menuTitle: MenuItem;
  checkin: MenuItem;
  calendar: MenuItem;
  profile: MenuItem;
  game: MenuItem;
  tournament: MenuItem;

  userData: any;

  sideBarTitle: string;
  sideBarCalendar: string;

  constructor(
    protected accountService: AccountService,
    protected gymservices: GymService,
    public globals: ThemeOptions,
    protected activatedRoute: ActivatedRoute,
    private sessionStorageService: SessionStorageService,
    public router: Router,
    private translateService: TranslateService
  ) {
  }

  load() {
    const storedData = this.sessionStorageService.retrieve('gym');
    let navMenu: MenuItem[] = [];
    this.accountService.identity().subscribe(data => {
      if (data?.authorities.includes('ROLE_USER') && !(data?.authorities.includes('ROLE_ADMIN'))) {
        this.menuTitle = {
          title: 'Indie',
          type: 'header',
        };
        navMenu.push(this.menuTitle);

        if (data?.authorities.includes('ROLE_USER')) {

          this.tournament = {
            title: 'Tournament',
            type: 'dropdown',
            link: '/tournament',
            active: false,
            icon: 'user',
            submenus: [
              {
                title: 'Add Tournament',
                type: 'simple',
                link: '/tournament/new',
                icon: 'list',
              },
            ],
          };

          this.game = {
            title: 'Game',
            type: 'dropdown',
            link: '/game',
            active: false,
            icon: 'id-badge',
            submenus: [
              {
                title: 'Add Game',
                type: 'simple',
                link: '/game/new',
                icon: 'list',
              },
            ],
          };

          navMenu.push(this.tournament);
          navMenu.push(this.game);
        }
      } else if (data?.authorities.includes('ROLE_BADGE')) {
        this.menuTitle = {
          title: "Player",
          type: 'header',
        };
        navMenu.push(this.menuTitle);

        this.tournament = {
          title: 'Tournament',
          type: 'simple',
          link: '/tournament',
          active: false,
          icon: 'user',
        };

        navMenu.push(this.tournament);
      }
      this.menus= navMenu;
    });
  }

  getMenuList() {
    this.load()
    if (this.accountService.hasAnyAuthority('ROLE_ADMIN')) {
      return this.menus.concat(this.adminMenu);
    }
    return this.menus;
  }

  getMenuItemByUrl(aMenus: MenuItem[], aUrl: string): MenuItem {
    for (const theMenu of aMenus) {
      if (theMenu.link && theMenu.link === aUrl) {
        return theMenu;
      }

      if (theMenu.submenus && theMenu.submenus.length > 0) {
        const foundItem = this.getMenuItemByUrl(theMenu.submenus, aUrl);
        if (foundItem) {
          return foundItem;
        }
      }
    }

    return undefined!;
  }

  toggleMenuItem(aMenus: MenuItem[], aCurrentMenu: MenuItem): MenuItem[] {
    return aMenus.map((aMenu: MenuItem) => {
      if (aMenu === aCurrentMenu) {
        aMenu.active = !aMenu.active;
      } else {
        aMenu.active = false;
      }

      if (aMenu.submenus && aMenu.submenus.length > 0) {
        aMenu.submenus = this.toggleMenuItem(aMenu.submenus, aCurrentMenu);

        if (aMenu.submenus.find(aChild => aChild.active)) {
          aMenu.active = true;
        }
      }

      return aMenu;
    });
  }
}
