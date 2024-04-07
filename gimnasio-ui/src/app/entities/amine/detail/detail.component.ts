import { Component, OnInit } from '@angular/core';
import { SharedService } from '../service/shared.service';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'jhi-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {

  constructor(private sharedService: SharedService, private router: Router, private route: ActivatedRoute, private location: Location) { }

  User : any = null;

  ngOnInit(): void {
    const userId = this.route.snapshot.paramMap.get('id');
    this.load(userId);
  }

  load(userId: any): void {
    this.sharedService.find(userId).subscribe(user => {
        this.User = user.body;
      });
  }

  Back(){
    this.location.back();
  }


}