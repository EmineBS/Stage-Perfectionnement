import { Component, OnInit } from '@angular/core';
import { ThemeOptions } from 'app/shared/theme-options';

@Component({
  selector: 'jhi-header-drawer',
  templateUrl: './header-drawer.component.html',
  styleUrls: ['./header-drawer.component.scss'],
})
export class HeaderDrawerComponent implements OnInit {
  constructor(public globals: ThemeOptions) {}

  toggleHeaderDrawerOpen() {
    this.globals.toggleHeaderDrawer = !this.globals.toggleHeaderDrawer;
  }
  ngOnInit(): void {}
}
