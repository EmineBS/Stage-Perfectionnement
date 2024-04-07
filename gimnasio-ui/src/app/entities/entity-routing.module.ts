import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NavBadgesComponent } from './gym-user/gym-badges/nav-badges/nav-badges.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BadgeRoutingResolveService } from './gym-user/gym-badges/route/badge-routing-resolve.service';
import { GymPacksComponent } from './gym-user/gym-packs/packs/gym-packs.component';
import { GymRoutingResolveService } from './gym-user/route/gym-routing-resolve.service';
import { NavPacksComponent } from './gym-user/gym-packs/nav-packs/nav-packs.component';
import { PackRoutingResolveService } from './pack/route/pack-routing-resolve.service';
import { FeatureRoutingResolveService } from './feature/route/feature-routing-resolve.service';
import { GymBadgesComponent } from './gym-user/gym-badges/badges/gym-badges.component';
import { DESC } from 'app/config/navigation.constants';
import { BadgeCalendarComponent } from './gym-user/badge-calendar/badge-calendar/badge-calendar.component';
import { GymCriteriasComponent } from './gym-user/gym-criterias/criterias/gym-criterias.component';
import { NavCriteriaComponent } from './criteria/nav-criteria/nav-criteria.component';
import { CriteriaRoutingResolveService } from './criteria/route/criteria-routing-resolve.service';
import { GymPackUpdateComponent } from './gym-user/gym-packs/update/gym-pack-update.component';
import { CheckinBadgeHistoryComponent } from './badge-checkin-history/checkin-badge-history.component';
import { GymFeaturesComponent } from './gym-user/gym-features/features/gym-features.component';
import { NavFeaturesComponent } from './gym-user/gym-features/nav-features/nav-features.component';

@NgModule({
  imports: [
    RouterModule.forChild([
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
      {
        path: 'gyms/:id',
        data: { pageTitle: 'gimnasio-uiApp.gym.home.title' },
        loadChildren: () => import('./gym-user/gym.module').then(m => m.GymModule),
      },
      {
        path: 'gyms/:id',
        data: { pageTitle: 'gimnasio-uiApp.gym.home.title' },
        children: [
          {
            path: 'badges/:id/checkin',
            data: { pageTitle: 'gimnasio-uiApp.checkin.home.title' },
            loadChildren: () => import('./checkin/checkin.module').then(m => m.CheckinModule),
          },
          {
            path: 'badges',
            component: GymBadgesComponent,
            data: {
              pageTitle: 'gimnasio-uiApp.badge.home.title',
            },
            resolve: {
              gym: GymRoutingResolveService,
            },
            canActivate: [UserRouteAccessService],
          },
          {
            path: 'badges/:uid/view',
            component: NavBadgesComponent,

            data: { pageTitle: 'gimnasio-uiApp.badge.home.title' },
            resolve: {
              badge: BadgeRoutingResolveService,
            },
            canActivate: [UserRouteAccessService],
          },
          {
            path: 'packs',
            component: GymPacksComponent,
            data: { pageTitle: 'gimnasio-uiApp.pack.home.title' },
            resolve: {
              gym: GymRoutingResolveService,
            },
          },
          {
            path: 'packs/:id/view',
            component: NavPacksComponent,
            resolve: {
              pack: PackRoutingResolveService,
            },
            canActivate: [UserRouteAccessService],
          },
          {
            path: 'add/pack',
            data: { pageTitle: 'gimnasio-uiApp.pack.detail.title' },
            component: GymPackUpdateComponent,
            resolve: {
              gym: GymRoutingResolveService,
            },
            canActivate: [UserRouteAccessService],
          },
          {
            path: 'criterias',
            component: GymCriteriasComponent,
            data: { pageTitle: 'gimnasio-uiApp.criteria.home.title' },
            resolve: {
              gym: GymRoutingResolveService,
            },
          },
          {
            path: 'criterias/:id/view',
            component: NavCriteriaComponent,
            resolve: {
              criteria: CriteriaRoutingResolveService,
            },
            canActivate: [UserRouteAccessService],
          },
          {
            path: 'features',
            component: GymFeaturesComponent,
            data: { pageTitle: 'gimnasio-uiApp.feature.home.title' },
            resolve: {
              gym: GymRoutingResolveService,
            },
          },
          {
            path: 'features/:id/view',
            component: NavFeaturesComponent,
            resolve: {
              feature: FeatureRoutingResolveService,
            },
            canActivate: [UserRouteAccessService],
          },
          {
            path: 'checkin',
            data: { pageTitle: 'gimnasio-uiApp.checkin.home.title' },
            loadChildren: () => import('./checkin/checkin.module').then(m => m.CheckinModule),
          },
          {
            path: 'calendar',
            data: { pageTitle: 'gimnasio-uiApp.calendar.home.title' },
            loadChildren: () => import('./gym-user/calendar/calendar.module').then(m => m.GymCalendarModule),
          },
        ],
      },
      {
        path: 'profile',
        data: { pageTitle: 'gimnasio-uiApp.profile.home.title' },
        loadChildren: () => import('./profile/profile.module').then(m => m.ProfileModule),
      },
      {
        path: 'badge-calendar',
        data: { pageTitle: 'gimnasio-uiApp.calendar.home.title' },
        loadChildren: () => import('./gym-user/badge-calendar/badge-calendar/badge-calendar.module').then(m => m.GymBadgeCalendarModule),
      },
      {
        path: 'progress',
        data: { pageTitle: 'gimnasio-uiApp.progress.home.title' },
        loadChildren: () => import('./progress/progress.module').then(m => m.ProgressModule),
      },
      {
        path: 'checkin',
        data: { pageTitle: 'gimnasio-uiApp.checkin.home.title' },
        loadChildren: () => import('./badge-checkin-history/checkin-badge-history.module').then(m => m.CheckinBadgeHistoryComponentModule),
      },
      {
        path: 'game',
        data: { pageTitle: 'gimnasio-uiApp.game.home.title' },
        loadChildren: () => import('./game/game.module').then(m => m.GameModule),
      },
      {
        path: 'tournament',
        data: { pageTitle: 'gimnasio-uiApp.tournament.home.title' },
        loadChildren: () => import('./tournament/tournament.module').then(m => m.TournamentModule),
      },
      
    ]),
  ],
})
export class EntityRoutingModule {}
