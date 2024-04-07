import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VisitComponent } from './list/visit.component';
import { VisitDetailComponent } from './detail/visit-detail.component';
import { VisitUpdateComponent } from './update/visit-update.component';
import { VisitDeleteDialogComponent } from './delete/visit-delete-dialog.component';
import { VisitRoutingModule } from './route/visit-routing.module';

@NgModule({
  imports: [SharedModule, VisitRoutingModule],
  declarations: [VisitComponent, VisitDetailComponent, VisitUpdateComponent, VisitDeleteDialogComponent],
})
export class VisitModule {}
