import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-nav-criterias',
  templateUrl: './nav-criterias.component.html',
  styleUrls: ['./nav-criterias.component.scss'],
})
export class NavCriteriasComponent implements OnInit {
  active: number;

  ngOnInit(): void {
    // Load the active tab from localStorage
    const savedActiveTab = localStorage.getItem('activeCriteriaTab');
    if (savedActiveTab) {
      this.active = parseInt(savedActiveTab, 10);
    } else {
      this.active = 1; // Default active tab if no value is saved
    }
  }

  changeActiveTab(tab: number): void {
    this.active = tab;
    // Save the active tab to localStorage
    localStorage.setItem('activeCriteriaTab', tab.toString());
  }
}
