import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IGame } from '../game.model';
import { GameService } from '../service/game.service';

@Component({
  selector: 'jhi-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss']
})
export class GameComponent implements OnInit {

  constructor(private gameService: GameService, private router: Router) { }

  games: IGame[];
  ngOnInit(): void {
    this.load();
  }

  remove(gameId: any): void {
    this.gameService.delete(gameId).subscribe(() => {
      this.load();
      this.router.navigate(['/game']);
    });
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
