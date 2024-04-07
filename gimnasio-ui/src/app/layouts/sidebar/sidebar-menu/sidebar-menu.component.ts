import { Compiler, Component, HostListener, Injector, OnInit, Type } from '@angular/core';
import { Router } from '@angular/router';
import { ThemeOptions } from 'app/shared/theme-options';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { SidebarMenuService } from './sidebar-menu.service';
import { LangChangeEvent, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'jhi-sidebar-menu',
  templateUrl: './sidebar-menu.component.html',
  styleUrls: ['./sidebar-menu.component.scss'],
  animations: [
    trigger('slide', [
      state('up', style({ height: 0, opacity: '0' })),
      state('down', style({ height: '*', opacity: '1' })),
      transition('up <=> down', animate(200)),
    ]),
  ],
})
export class SidebarMenuComponent implements OnInit {
  menus: any[];

  constructor(public globals: ThemeOptions, private sidebarMenuService: SidebarMenuService, private router: Router, private translateService: TranslateService) {
    this.menus = [...sidebarMenuService.getMenuList()];
  }

  private innerWidth: number;

  ngOnInit() {
    const theActiveMenu = this.sidebarMenuService.getMenuItemByUrl(this.menus, this.router.url);
    if (theActiveMenu) {
      this.toggle(theActiveMenu);
    }
    this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.menus = [...this.sidebarMenuService.getMenuList()];
    });

    this.innerWidth = window.innerWidth;
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.innerWidth = window.innerWidth;
  }

  toggleSidebarMobileOpen() {
    if (this.innerWidth < 992) {
      this.globals.toggleSidebarMobile = !this.globals.toggleSidebarMobile;
      this.globals.toggleSidebar = false;
    }
  }

  toggle(currentMenu: any) {
    console.log("currentMenu:")
    console.log(currentMenu)
    this.menus = this.sidebarMenuService.toggleMenuItem(this.menus, currentMenu);
  }

  getState(currentMenu: any) {
    if (currentMenu.active) {
      return 'down';
    } else {
      return 'up';
    }
  }
}
