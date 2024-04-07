import { Component, OnInit } from '@angular/core';
import { SharedService } from '../service/shared.service';
import { Router } from '@angular/router';
import { finalize, Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { IAmine, Amine } from '../amine.model';


@Component({
  selector: 'jhi-ajout',
  templateUrl: './ajout.component.html',
  styleUrls: ['./ajout.component.scss']
})
export class AjoutAmineComponent implements OnInit {

  constructor(private sharedService: SharedService, private router: Router) { }
  myName : String;
  familyName : String;
  age : Number;

  submit(){
    const user:IAmine = new Amine(this.myName, this.familyName, this.age);
    this.subscribeToSaveResponse(this.sharedService.create(user));
  }
  
  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAmine>>): void {
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
    this.router.navigate(['/admin/amine']);
  }

  ngOnInit(): void {
  }

}
