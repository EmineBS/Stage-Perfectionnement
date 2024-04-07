import { Component, OnInit } from '@angular/core';
import { IGame} from '../game.model';
import { GameService } from '../service/game.service';
import { Router } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { finalize, Observable } from 'rxjs';


@Component({
  selector: 'jhi-ajout',
  templateUrl: './ajout.component.html',
  styleUrls: ['./ajout.component.scss']
})
export class AjoutComponent implements OnInit {

  startTimeDate: string;

  constructor(private gameService:GameService, private router:Router) { }
  game: IGame = {
    id: '',
    gameName: '',
    style: ''
  };

  ngOnInit(): void {
  }

  submit(){
    console.log(this.game);
    this.subscribeToSaveResponse(this.gameService.create(this.game));
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGame>>): void {
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
    this.router.navigate(['/game']);
  }

}
