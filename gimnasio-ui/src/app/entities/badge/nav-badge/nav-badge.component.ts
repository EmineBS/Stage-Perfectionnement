import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-nav-badge',
  templateUrl: './nav-badge.component.html',
  styleUrls: ['./nav-badge.component.scss'],
})
export class NavBadgeComponent implements OnInit {
  active: number;

  ngOnInit(): void {
    // Load the active tab from localStorage
    const savedActiveTab = localStorage.getItem('activeBadgeTab');
    if (savedActiveTab) {
      this.active = parseInt(savedActiveTab, 10);
    } else {
      this.active = 1; // Default active tab if no value is saved
    }
  }

  changeActiveTab(tab: number): void {
    this.active = tab;
    // Save the active tab to localStorage
    localStorage.setItem('activeBadgeTab', tab.toString());
  }
}
