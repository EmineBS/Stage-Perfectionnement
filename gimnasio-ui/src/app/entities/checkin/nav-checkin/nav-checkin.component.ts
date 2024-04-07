import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-nav-checkin',
  templateUrl: './nav-checkin.component.html',
  styleUrls: ['./nav-checkin.component.scss'],
})
export class NavCheckinComponent implements OnInit {
  active: number;

  ngOnInit(): void {
    // Load the active tab from localStorage
    const savedActiveTab = localStorage.getItem('activeCheckinTab');
    if (savedActiveTab) {
      this.active = parseInt(savedActiveTab, 10);
    } else {
      this.active = 1; // Default active tab if no value is saved
    }
  }

  changeActiveTab(tab: number): void {
    this.active = tab;
    // Save the active tab to localStorage
    localStorage.setItem('activeCheckinTab', tab.toString());
  }
}
