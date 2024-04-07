import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { LANGUAGES } from 'app/config/language.constants';
import { StateStorageService } from 'app/core/auth/state-storage.service';
import { SessionStorageService } from 'ngx-webstorage';

@Component({
  selector: 'jhi-sidebar-footer',
  templateUrl: './sidebar-footer.component.html',
  styleUrls: ['./sidebar-footer.component.scss'],
})
export class SidebarFooterComponent implements OnInit {

  languages = LANGUAGES;
  isNavbarCollapsed = true;

  constructor(private sessionStorageService: SessionStorageService,private translateService: TranslateService,) {}

  ngOnInit(): void {
    console.log("langages"+ this.languages)
  }

  changeLanguage(languageKey: string): void {
    this.sessionStorageService.store('locale', languageKey);
    this.translateService.use(languageKey);
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }
}
