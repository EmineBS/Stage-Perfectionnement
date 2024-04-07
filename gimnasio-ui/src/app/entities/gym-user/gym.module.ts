import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { GymRoutingModule } from './route/gym-routing.module';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { GymUpdateComponent } from './update/gym-update.component';
import { GymDetailComponent } from './detail/gym-detail.component';
import { NavGymComponent } from './nav-gym/nav-gym.component';
import { GymBadgesComponent } from './gym-badges/badges/gym-badges.component';
import { ViewPackComponent } from './gym-packs/view-pack/view-pack.component';
import { GymPacksComponent } from './gym-packs/packs/gym-packs.component';
import { NavBadgesComponent } from './gym-badges/nav-badges/nav-badges.component';
import { NavPacksComponent } from './gym-packs/nav-packs/nav-packs.component';
import { CheckinBadgeComponent } from './gym-badges/checkin-badge/checkin-badge.component';
import { BadgePackComponent } from './gym-badges/badge-pack/badge-pack.component';
import { DeleteComponent } from './gym-badges/badge-pack/delete/delete.component';
import { ViewBadgeComponent } from './gym-badges/badges/view-badge/view-badge.component';
import { VerifyComponent } from 'app/layouts/checkin-badge/verify/verify.component';
import { DeactivateBadgeComponent } from './gym-badges/badges/deactivate-badge/deactivate-badge.component';
import { ActivateBadgeComponent } from './gym-badges/badges/activate-badge/activate-badge.component';
import { ProfileDetailComponent } from './gym-badges/profile-detail/profile-detail.component';
import { GymCriteriasComponent } from './gym-criterias/criterias/gym-criterias.component';
import { NotifierModule, NotifierOptions, NotifierService } from 'angular-notifier';
import { ProgressComponent } from './gym-badges/progress/progress.component';
import { NgApexchartsModule } from 'ng-apexcharts';
import { GymPackUpdateComponent } from './gym-packs/update/gym-pack-update.component';
import { GymFeaturesComponent } from './gym-features/features/gym-features.component';
import { NavFeaturesComponent } from './gym-features/nav-features/nav-features.component';
import { ViewFeatureComponent } from './gym-features/view-feature/view-feature.component';
/**
 * Custom angular notifier options
 */
const customNotifierOptions: NotifierOptions = {
  position: {
    horizontal: {
      position: 'right',
      distance: 12,
    },
    vertical: {
      position: 'top',
      distance: 12,
      gap: 10,
    },
  },
  theme: 'uifort',
  behaviour: {
    autoHide: 5000,
    onClick: 'hide',
    onMouseover: 'pauseAutoHide',
    showDismissButton: true,
    stacking: 4,
  },
  animations: {
    enabled: true,
    show: {
      preset: 'slide',
      speed: 300,
      easing: 'ease',
    },
    hide: {
      preset: 'fade',
      speed: 300,
      easing: 'ease',
      offset: 50,
    },
    shift: {
      speed: 300,
      easing: 'ease',
    },
    overlap: 150,
  },
};

@NgModule({
  imports: [
    SharedModule,
    GymRoutingModule,
    BsDropdownModule.forRoot(),
    NotifierModule.withConfig(customNotifierOptions),
    NgApexchartsModule,
  ],
  declarations: [
    GymDetailComponent,
    GymUpdateComponent,
    NavGymComponent,
    GymBadgesComponent,
    GymPacksComponent,
    GymPackUpdateComponent,
    ViewBadgeComponent,
    ViewPackComponent,
    NavBadgesComponent,
    VerifyComponent,
    NavPacksComponent,
    CheckinBadgeComponent,
    BadgePackComponent,
    DeleteComponent,
    DeactivateBadgeComponent,
    ActivateBadgeComponent,
    ProfileDetailComponent,
    GymCriteriasComponent,
    ProgressComponent,
    GymFeaturesComponent,
    NavFeaturesComponent,
    ViewFeatureComponent
  ],
  providers: [NotifierService],
})
export class GymModule {}
