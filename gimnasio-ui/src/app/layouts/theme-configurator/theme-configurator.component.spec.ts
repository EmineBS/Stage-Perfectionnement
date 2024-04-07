import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ThemeConfiguratorComponent } from './theme-configurator.component';

describe('ThemeConfiguratorComponent', () => {
  let component: ThemeConfiguratorComponent;
  let fixture: ComponentFixture<ThemeConfiguratorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ThemeConfiguratorComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ThemeConfiguratorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
