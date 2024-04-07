import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { WelcomeRoutingModule } from './route/welcome-routing.module';
import { WelcomePageComponent } from './welcome-page.component';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [WelcomeRoutingModule, SharedModule, RouterModule],
  declarations: [WelcomePageComponent],
})
export class WelcomeModule {}
