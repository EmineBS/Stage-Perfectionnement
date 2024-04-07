import { Component, OnInit } from '@angular/core';
import { ThemeOptions } from 'app/shared/theme-options';

@Component({
  selector: 'jhi-left-sidebar',
  templateUrl: './left-sidebar.component.html',
  styleUrls: ['./left-sidebar.component.scss'],
})
export class LeftSidebarComponent implements OnInit {
  constructor(public globals: ThemeOptions) {}
  ngOnInit(): void {}

  toggleSidebarMobileOpen() {
    this.globals.toggleSidebarMobile = !this.globals.toggleSidebarMobile;
    this.globals.toggleSidebar = false;
  }
}
