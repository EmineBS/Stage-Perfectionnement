import { Component, OnInit } from '@angular/core';
import { GymService } from 'app/entities/gym-user/service/gym.service';
import { ThemeOptions } from 'app/shared/theme-options';
import { SessionStorageService } from 'ngx-webstorage';

@Component({
  selector: 'jhi-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
})
export class SidebarComponent implements OnInit {
  constructor(public globals: ThemeOptions) {}
  ngOnInit(): void {}

  toggleSidebarMobileOpen() {
    this.globals.toggleSidebarMobile = !this.globals.toggleSidebarMobile;
    this.globals.toggleSidebar = false;
  }
}
