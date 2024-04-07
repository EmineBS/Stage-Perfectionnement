import { Component, OnInit } from '@angular/core';
import { ThemeOptions } from 'app/shared/theme-options';

@Component({
  selector: 'jhi-sidebar-collapsed',
  templateUrl: './sidebar-collapsed.component.html',
  styleUrls: ['./sidebar-collapsed.component.scss'],
})
export class SidebarCollapsedComponent implements OnInit {
  constructor(public globals: ThemeOptions) {}

  ngOnInit(): void {}
  toggleSidebarMobileOpen() {
    this.globals.toggleSidebarMobile = !this.globals.toggleSidebarMobile;
    this.globals.toggleSidebar = false;
  }
}
