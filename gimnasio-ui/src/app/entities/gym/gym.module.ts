import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { GymComponent } from './list/gym.component';
import { GymDetailComponent } from './detail/gym-detail.component';
import { GymUpdateComponent } from './update/gym-update.component';
import { GymDeleteDialogComponent } from './delete/gym-delete-dialog.component';
import { GymRoutingModule } from './route/gym-routing.module';
import { NavGymComponent } from './nav-gym/nav-gym.component';
import { GymBadgesComponent } from './gym-badges/gym-badges.component';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { GymPacksComponent } from './gym-packs/gym-packs.component';
import { CloseGymComponent } from './close-gym/close-gym.component';
import { GymUsersComponent } from './gym-users/list/gym-users.component';
import { DeleteUserFromGymComponent } from './gym-users/delete-user-from-gym/delete-user-from-gym.component';
import { ViewUserModalComponent } from './gym-users/view-user-modal/view-user-modal.component';
import { ActivateGymComponent } from './activate-gym/activate-gym.component';
import { GymFeautersComponent } from './gym-features/list/gym-features.component';
import { DeleteFeatureFromGymComponent } from './gym-features/delete-feature-from-gym/delete-feature-from-gym.component';
import { GymAmineComponent } from './gym-amine/gym-amine.component';

@NgModule({
  imports: [SharedModule, GymRoutingModule, BsDropdownModule.forRoot()],
  declarations: [
    GymComponent,
    GymDetailComponent,
    GymUpdateComponent,
    GymDeleteDialogComponent,
    NavGymComponent,
    GymBadgesComponent,
    GymPacksComponent,
    CloseGymComponent,
    GymUsersComponent,
    DeleteUserFromGymComponent,
    ViewUserModalComponent,
    ActivateGymComponent,
    GymFeautersComponent,
    DeleteFeatureFromGymComponent,
    GymAmineComponent
  ],
})
export class GymModule {}
