import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-nav-features',
  templateUrl: './nav-features.component.html',
  styleUrls: ['./nav-features.component.scss'],
})
export class NavFeaturesComponent implements OnInit {
  active: number;

  ngOnInit(): void {
    // Load the active tab from localStorage
    const savedActiveTab = localStorage.getItem('activeFeatureTab');
    if (savedActiveTab) {
      this.active = parseInt(savedActiveTab, 10);
    } else {
      this.active = 1; // Default active tab if no value is saved
    }
  }

  changeActiveTab(tab: number): void {
    this.active = tab;
    // Save the active tab to localStorage
    localStorage.setItem('activeFeatureTab', tab.toString());
  }
}
