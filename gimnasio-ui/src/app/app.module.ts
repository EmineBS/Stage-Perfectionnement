import { NgModule, LOCALE_ID } from '@angular/core';
import { registerLocaleData } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import locale from '@angular/common/locales/en';
import { BrowserModule, Title } from '@angular/platform-browser';
import { ServiceWorkerModule } from '@angular/service-worker';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { NgxWebstorageModule } from 'ngx-webstorage';
import dayjs from 'dayjs/esm';
import { NgbDateAdapter, NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';

import { ScrollingModule } from '@angular/cdk/scrolling';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import './config/dayjs';
import { SharedModule } from 'app/shared/shared.module';
import { TranslationModule } from 'app/shared/language/translation.module';
import { AppRoutingModule } from './app-routing.module';
import { HomeModule } from './home/home.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { NgbDateDayjsAdapter } from './config/datepicker-adapter';
import { fontAwesomeIcons } from './config/font-awesome-icons';
import { httpInterceptorProviders } from 'app/core/interceptor/index';
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { WelcomePageComponent } from './entities/welcome-page/welcome-page.component';
import { WelcomeRoutingModule } from './entities/welcome-page/route/welcome-routing.module';
import { SidebarComponent } from './layouts/sidebar/sidebar.component';
import { ThemeConfiguratorComponent } from './layouts/theme-configurator/theme-configurator.component';
import { HeaderComponent } from './layouts/header/header.component';
import { HeaderDotsComponent } from './layouts/header/header-dots/header-dots.component';
import { HeaderDrawerComponent } from './layouts/header/header-drawer/header-drawer.component';
import { HeaderMenuComponent } from './layouts/header/header-menu/header-menu.component';
import { HeaderSearchComponent } from './layouts/header/header-search/header-search.component';
import { HeaderUserboxComponent } from './layouts/header/header-userbox/header-userbox.component';
import { ThemeOptions } from './shared/theme-options';
import { IconsModule } from './shared/icons/icons.module';
import { SidebarCollapsedComponent } from './layouts/sidebar/sidebar-collapsed/sidebar-collapsed.component';
import { SidebarFooterComponent } from './layouts/sidebar/sidebar-footer/sidebar-footer.component';
import { SidebarHeaderComponent } from './layouts/sidebar/sidebar-header/sidebar-header.component';
import { SidebarMenuComponent } from './layouts/sidebar/sidebar-menu/sidebar-menu.component';
import { SidebarUserboxComponent } from './layouts/sidebar/sidebar-userbox/sidebar-userbox.component';
import { PageTitleComponent } from './layouts/page-title/page-title.component';
import { ModalComponent } from './layouts/modal/modal.component';
import { LeftSidebarComponent } from './layouts/sidebar/left-sidebar/left-sidebar.component';
import { NgSelectModule } from '@ng-select/ng-select';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CheckinBadgeComponent } from './layouts/checkin-badge/checkin-badge.component';
import { CheckinModule } from './entities/checkin/checkin.module';
import { CheckinResultComponent } from './layouts/checkin-badge/checkin-result/checkin-result.component';

import { BsDropdownModule } from 'ngx-bootstrap/dropdown';

@NgModule({
  imports: [
    BrowserModule,
    SharedModule,
    WelcomeRoutingModule,
    HomeModule,
    CheckinModule,
    AppRoutingModule,
    IconsModule,
    ScrollingModule,
    // jhipster-needle-angular-add-module JHipster will add new module here

    // Set this to true to enable service worker (PWA)
    ServiceWorkerModule.register('ngsw-worker.js', { enabled: false }),
    HttpClientModule,
    NgxWebstorageModule.forRoot({ prefix: 'jhi', separator: '-', caseSensitive: true }),
    TranslationModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    FormsModule,
    NgSelectModule,
    BrowserAnimationsModule,
    BsDropdownModule.forRoot(),
  ],
  providers: [
    Title,
    { provide: LOCALE_ID, useValue: 'en' },
    { provide: NgbDateAdapter, useClass: NgbDateDayjsAdapter },
    httpInterceptorProviders,
    ThemeOptions,
  ],
  declarations: [
    MainComponent,
    NavbarComponent,
    ErrorComponent,
    PageRibbonComponent,
    ActiveMenuDirective,
    FooterComponent,
    WelcomePageComponent,
    SidebarComponent,
    ThemeConfiguratorComponent,
    HeaderComponent,
    HeaderDotsComponent,
    HeaderDrawerComponent,
    HeaderMenuComponent,
    HeaderSearchComponent,
    HeaderUserboxComponent,
    SidebarCollapsedComponent,
    SidebarFooterComponent,
    SidebarHeaderComponent,
    SidebarMenuComponent,
    SidebarUserboxComponent,
    PageTitleComponent,
    ModalComponent,
    LeftSidebarComponent,
    CheckinBadgeComponent,
    CheckinResultComponent,
  ],
  bootstrap: [MainComponent],
})
export class AppModule {
  constructor(applicationConfigService: ApplicationConfigService, iconLibrary: FaIconLibrary, dpConfig: NgbDatepickerConfig) {
    applicationConfigService.setEndpointPrefix(SERVER_API_URL);
    registerLocaleData(locale);
    iconLibrary.addIcons(...fontAwesomeIcons);
    dpConfig.minDate = { year: dayjs().subtract(100, 'year').year(), month: 1, day: 1 };
  }
}
