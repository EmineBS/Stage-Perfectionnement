import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarUserboxComponent } from './sidebar-userbox.component';

describe('SidebarUserboxComponent', () => {
  let component: SidebarUserboxComponent;
  let fixture: ComponentFixture<SidebarUserboxComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SidebarUserboxComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(SidebarUserboxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
