import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

@NgModule({
  imports: [
    /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
    RouterModule.forChild([
      
      {
        path: 'docs',
        loadChildren: () => import('./docs/docs.module').then(m => m.DocsModule),
      },
      {
        path: 'configuration',
        loadChildren: () => import('./configuration/configuration.module').then(m => m.ConfigurationModule),
      },
      {
        path: 'health',
        loadChildren: () => import('./health/health.module').then(m => m.HealthModule),
      },
      {
        path: 'logs',
        loadChildren: () => import('./logs/logs.module').then(m => m.LogsModule),
      },
      {
        path: 'metrics',
        loadChildren: () => import('./metrics/metrics.module').then(m => m.MetricsModule),
      },
      {
        path: 'gateway',
        loadChildren: () => import('./gateway/gateway.module').then(m => m.GatewayModule),
      },
      {
        path: 'gym',
        data: { pageTitle: 'gimnasio-uiApp.gym.home.title' },
        loadChildren: () => import('../entities/gym/gym.module').then(m => m.GymModule),
      },
      {
        path: 'gym/:id',
        data: { pageTitle: 'gimnasio-uiApp.gym.home.title' },
        children: [
          {
            path: 'badge',
            data: { pageTitle: 'gimnasio-uiApp.badge.home.title' },
            loadChildren: () => import('../entities/badge/badge.module').then(m => m.BadgeModule),
          },

          {
            path: 'pack',
            data: { pageTitle: 'gimnasio-uiApp.pack.home.title' },
            loadChildren: () => import('../entities/pack/pack.module').then(m => m.PackModule),
          },
        ],
      },
      {
        path: 'badge',
        data: { pageTitle: 'gimnasio-uiApp.badge.home.title' },
        loadChildren: () => import('../entities/badge/badge.module').then(m => m.BadgeModule),
      },

      {
        path: 'badge/:id',
        data: { pageTitle: 'gimnasio-uiApp.gym.home.title' },
        children: [
          {
            path: 'checkin',
            data: { pageTitle: 'gimnasio-uiApp.checkin.home.title' },
            loadChildren: () => import('../entities/checkin/checkin.module').then(m => m.CheckinModule),
          },
        ],
      },
      {
        path: 'pack',
        data: { pageTitle: 'gimnasio-uiApp.pack.home.title' },
        loadChildren: () => import('../entities/pack/pack.module').then(m => m.PackModule),
      },
      {
        path: 'criteria',
        data: { pageTitle: 'gimnasio-uiApp.criteria.home.title' },
        loadChildren: () => import('../entities/criteria/criteria.module').then(m => m.CriteriaModule),
      },
      {
        path: 'feature',
        data: { pageTitle: 'gimnasio-uiApp.feature.home.title' },
        loadChildren: () => import('../entities/feature/feature.module').then(m => m.FeatureModule),
      },
      {
        path: 'user',
        data: { pageTitle: 'userManagement.home.title' },
        loadChildren: () => import('../entities/user/user.module').then(m => m.UserModule),
      },
      {
        path: 'amine',
        data: { pageTitle: 'gimnasio-uiApp.amine.home.title' },
        loadChildren: () => import('../entities/amine/amine.module').then(m => m.AmineModule),
      },
      {
        path: 'events',
        data: { pageTitle: 'gimnasio-uiApp.events.home.title' },
        loadChildren: () => import('../entities/events/events.module').then(m => m.EventsModule),
      },
      {
        path: 'store',
        data: { pageTitle: 'gimnasio-uiApp.store.home.title' },
        loadChildren: () => import('../entities/store/store.module').then(m => m.StoreModule),
      },
      /* jhipster-needle-add-admin-route - JHipster will add admin routes here */
    ]),
  ],
})
export class AdminRoutingModule {}
