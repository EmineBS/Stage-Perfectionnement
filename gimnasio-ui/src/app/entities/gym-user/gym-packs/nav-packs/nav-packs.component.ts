import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-nav-packs',
  templateUrl: './nav-packs.component.html',
  styleUrls: ['./nav-packs.component.scss'],
})
export class NavPacksComponent implements OnInit {
  active: number;

  ngOnInit(): void {
    // Load the active tab from localStorage
    const savedActiveTab = localStorage.getItem('activePackTab');
    if (savedActiveTab) {
      this.active = parseInt(savedActiveTab, 10);
    } else {
      this.active = 1; // Default active tab if no value is saved
    }
  }

  changeActiveTab(tab: number): void {
    this.active = tab;
    // Save the active tab to localStorage
    localStorage.setItem('activePackTab', tab.toString());
  }
}
