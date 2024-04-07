import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { TournamentComponent } from './list/tournament.component';
import { TournamentRoutingModule } from './route/tournament-routing.module';
import { AjoutComponent } from './ajout/ajout.component';
import { DatePipe } from '@angular/common';

@NgModule({
    imports: [SharedModule, TournamentRoutingModule, BsDropdownModule.forRoot()],
    declarations: [
      TournamentComponent,
      AjoutComponent,
    ],
    providers: [DatePipe],
  })
  export class TournamentModule {}