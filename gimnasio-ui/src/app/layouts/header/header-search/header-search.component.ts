import { Component, OnInit } from '@angular/core';
import { ThemeOptions } from 'app/shared/theme-options';

@Component({
  selector: 'jhi-header-search',
  templateUrl: './header-search.component.html',
  styleUrls: ['./header-search.component.scss'],
})
export class HeaderSearchComponent implements OnInit {
  constructor(public globals: ThemeOptions) {}

  searchActive = false;
  ngOnInit(): void {}
}
