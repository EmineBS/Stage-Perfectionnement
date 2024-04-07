import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { Router } from '@angular/router';
import { TournamentService } from '../service/tournament.service';
import { ITour } from '../tournament.model';
import { SessionStorageService } from 'ngx-webstorage';
import { HttpResponse } from '@angular/common/http';
import { GameService } from 'app/entities/game/service/game.service';
import { finalize, Observable } from 'rxjs';
import { IGame } from 'app/entities/game/game.model';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-tournament',
  templateUrl: './tournament.component.html',
  styleUrls: ['./tournament.component.scss']
})
export class TournamentComponent implements OnInit {

  activeGame: String | null = null;
  activeGameId: String | null = null;
  games: IGame[];
  role: any;

  constructor(private tournamentService: TournamentService, private gameService:GameService, private datePipe: DatePipe, private router: Router, private sessionStorageService: SessionStorageService, private accountService: AccountService) { }
  tournaments: ITour[];
  ngOnInit(): void {
    this.loadGames();
    if (this.sessionStorageService.retrieve('game')){
      this.activeGame = this.sessionStorageService.retrieve('game').gameName;
      this.activeGameId = this.sessionStorageService.retrieve('game').id;
    }
    this.load();
    console.log(this.accountService);
  }

  selectTournamentsPerGame(tempTournaments:ITour[]){
    let selectedToursPerGame: ITour[]=[];
    if(this.activeGameId!==null){
      for(let i=0; i<tempTournaments.length; i++){
        if(tempTournaments[i].gameId==this.activeGameId){
          selectedToursPerGame.push(tempTournaments[i]);
        }
      }
    }
    if(selectedToursPerGame.length !== 0){
      this.tournaments=selectedToursPerGame;
    }
  }

  load(): void {
    this.tournamentService.query().subscribe(
      (response: HttpResponse<ITour[]>): void => {
        if(response.body!==null){
          const tempTouranments = response.body;
          if (this.accountService.hasAnyAuthority(['ROLE_USER'])){
            this.selectTournamentsPerGame(tempTouranments);
            this.role="indie";
          }else if(this.accountService.hasAnyAuthority(['ROLE_BADGE'])){
            this.tournaments=tempTouranments;
            this.role="player";
          }
        }
      },
      (error) => {
        console.error(error);
      }
    );
  }

  loadGames(){
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

  getGameName(gameId: String | null) : String | undefined{
    if (!this.games || !Array.isArray(this.games) || this.games.length === 0) {
      return undefined;
    }

    for (const game of this.games) {
      if (game.id === gameId && game.gameName!==null) {
        return game.gameName;
      }
    }

      return undefined;
  }

  submit(tournament:ITour){
    console.log(tournament);
  }

  Close(tournament:ITour){
    tournament.registration=false;
    this.router.navigate(['/tournament']);
  }

  Delete(tourId: any){
    this.tournamentService.delete(tourId).subscribe(() => {
      this.load();
      this.router.navigate(['/tournament']);
    });
  }

  Activate(tournament:ITour){
    tournament.registration=true;
    this.router.navigate(['/tournament']);
  }

  convertTimestampToDateString(timestamp: number | null): string {
    if (timestamp!==null){
      const milliseconds = timestamp;
      const formattedDate = this.datePipe.transform(milliseconds, 'yyyy-MM-dd HH:mm:ss');
      if (formattedDate !== null) {
        return formattedDate;
      } else {
        return '';
      }
    }else{
      return '';
    }
  }

  registration(state: Boolean | null) : string {
    if(state===true){
      return "Open";
    }else{
      return "Close";
    }
  }
  
}
