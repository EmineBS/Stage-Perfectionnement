import { Component, OnInit } from '@angular/core';
import { ITour } from '../tournament.model';
import { Router } from '@angular/router';
import { TournamentService } from '../service/tournament.service';
import { GameService } from 'app/entities/game/service/game.service';
import { IGame } from 'app/entities/game/game.model';
import { HttpResponse } from '@angular/common/http';
import { finalize, Observable } from 'rxjs';

@Component({
  selector: 'jhi-ajout',
  templateUrl: './ajout.component.html',
  styleUrls: ['./ajout.component.scss']
})
export class AjoutComponent implements OnInit {

  startTimeDate: string;
  games:IGame[] = [];
  
  constructor(private tournamentService:TournamentService, private gameService:GameService, private router:Router) { }
  tournament: ITour = {
    id: null,
    name: '',
    registration: false,
    starttimestamp: 0,
    minplayers: 0,
    maxplayers: 0,
    gameId: '',
  };

  ngOnInit(): void {
    this.load();
  }

  submit(){
    this.subscribeToSaveResponse(this.tournamentService.create(this.tournament));
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITour>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  onSaveFinalize(): void {
    console.log("Save End!");
  }
  onSaveError(): void {
    console.log("Save Fail!");
  }
  onSaveSuccess(): void {
    this.router.navigate(['/tournament']);
  }

  onStartTimeChange(){
    this.tournament.starttimestamp = new Date(this.startTimeDate).getTime();
  }

  load(): void {
    this.gameService.query().subscribe(
      (response: HttpResponse<IGame[]>): void => {
        if(response.body!==null){
          this.games = response.body;
        }
      },
      (error) => {
        console.error(error);
      }
    );
  }

}
