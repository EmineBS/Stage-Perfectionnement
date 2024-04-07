import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { GameComponent } from './list/game.component';
import { GameRoutingModule } from './route/game-routing.module';
import { AjoutComponent } from './ajout/ajout.component';


@NgModule({
    imports: [SharedModule, GameRoutingModule, BsDropdownModule.forRoot()],
    declarations: [
      GameComponent,
      AjoutComponent,
    ],
  })
  export class GameModule {}