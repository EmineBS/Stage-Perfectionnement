import { Component, OnInit } from '@angular/core';
import { ThemeOptions } from 'app/shared/theme-options';

@Component({
  selector: 'jhi-theme-configurator',
  templateUrl: './theme-configurator.component.html',
  styleUrls: ['./theme-configurator.component.scss'],
})
export class ThemeConfiguratorComponent implements OnInit {
  // Sidebar options

  toggleSidebarTheme(color: any, style: any, lighter: any) {
    this.globals.sidebarBackground = color;
    this.globals.sidebarBackgroundStyle = style;
    this.globals.sidebarLighterStyle = lighter;

    if (style === 'light' || lighter) {
      this.globals.sidebarShadow = true;
    } else {
      this.globals.sidebarShadow = false;
    }
  }

  toggleSidebarFooter() {
    this.globals.sidebarFooter = !this.globals.sidebarFooter;
  }

  toggleSidebarUserbox() {
    this.globals.sidebarUserbox = !this.globals.sidebarUserbox;
  }

  toggleSidebarShadow() {
    this.globals.sidebarShadow = !this.globals.sidebarShadow;
  }

  toggleSidebarFixed() {
    this.globals.sidebarFixed = !this.globals.sidebarFixed;
  }

  // Header options

  toggleHeaderFixed() {
    this.globals.headerFixed = !this.globals.headerFixed;
  }

  toggleHeaderShadow() {
    this.globals.headerShadow = !this.globals.headerShadow;
  }

  toggleHeaderTransparentBg() {
    this.globals.headerTransparentBg = !this.globals.headerTransparentBg;
  }

  // Footer options

  toggleFooterFixed() {
    this.globals.footerFixed = !this.globals.footerFixed;
  }

  toggleFooterShadow() {
    this.globals.footerShadow = !this.globals.footerShadow;
  }

  toggleFooterTransparentBg() {
    this.globals.footerTransparentBg = !this.globals.footerTransparentBg;
  }

  // Page title options

  togglePageTitleIconBox() {
    this.globals.pageTitleIconBox = !this.globals.pageTitleIconBox;
  }

  togglePageTitleShadow() {
    this.globals.pageTitleShadow = !this.globals.pageTitleShadow;
  }

  togglePageTitleBreadcrumb() {
    this.globals.pageTitleBreadcrumb = !this.globals.pageTitleBreadcrumb;
  }

  togglePageTitleStyle(style: any, shadow: any) {
    this.globals.pageTitleStyle = style;
    this.globals.pageTitleBreadcrumb = false;

    if (shadow) {
      this.globals.pageTitleShadow = true;
    } else {
      this.globals.pageTitleShadow = false;
    }
  }

  togglePageTitleTheme(color: any) {
    this.globals.pageTitleBackground = color;
  }

  togglePageTitleDescription() {
    this.globals.pageTitleDescription = !this.globals.pageTitleDescription;
  }

  // Main content options

  toggleContentTheme(color: any) {
    this.globals.contentBackground = color;
  }
  constructor(public globals: ThemeOptions) {}

  ngOnInit(): void {}
}
