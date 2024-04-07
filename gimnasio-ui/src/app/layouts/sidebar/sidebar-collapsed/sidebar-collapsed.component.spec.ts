import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarCollapsedComponent } from './sidebar-collapsed.component';

describe('SidebarCollapsedComponent', () => {
  let component: SidebarCollapsedComponent;
  let fixture: ComponentFixture<SidebarCollapsedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SidebarCollapsedComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(SidebarCollapsedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
