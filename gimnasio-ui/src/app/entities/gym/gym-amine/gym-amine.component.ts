import { Component, OnInit, ViewChild, ElementRef} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';



@Component({
  selector: 'jhi-gym-amine',
  templateUrl: './gym-amine.component.html',
  styleUrls: ['./gym-amine.component.scss']
})
export class GymAmineComponent implements OnInit {
  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router
  ){}

  Me = new Self("Amine","BENSALAH",21);

  isEditable = false;
  edit(): void{
    this.isEditable = !this.isEditable;
  }

  ngOnInit(): void {
  }

}

class Self{
  myName : String;
  familyName : String;
  age : Number;
  constructor(myName : String, familyName : String, age : Number){
    this.myName=myName;
    this.familyName=familyName;
    this.age=age;
  }
}
