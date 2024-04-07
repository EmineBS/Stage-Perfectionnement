import { Component, Input, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRouteSnapshot } from '@angular/router';
import { faUmbrella } from '@fortawesome/free-solid-svg-icons';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Umbrella } from 'angular-feather/icons';
import { ThemeOptions } from '../../shared/theme-options';

@Component({
  selector: 'jhi-page-title',
  templateUrl: './page-title.component.html',
  styleUrls: ['./page-title.component.scss'],
})
export class PageTitleComponent implements OnInit {
  umbrella = faUmbrella;
  closeResult: string;
  constructor(public globals: ThemeOptions, private titleService: Title, private modalService: NgbModal) {}

  ngOnInit(): void {}
  private getPageTitle(routeSnapshot: ActivatedRouteSnapshot): string {
    const title: string = routeSnapshot.data['pageTitle'] ?? '';
    if (routeSnapshot.firstChild) {
      return this.getPageTitle(routeSnapshot.firstChild) || title;
    }
    return title;
  }

  @Input() titleHeading: string;
  @Input() titleDescription: string;
}
