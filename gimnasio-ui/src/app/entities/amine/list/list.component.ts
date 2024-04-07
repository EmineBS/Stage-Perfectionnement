import { Component, OnInit } from '@angular/core';
import { SharedService } from '../service/shared.service';
import { Router } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { IAmine, Amine } from '../amine.model';



@Component({
  selector: 'jhi-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {

  constructor(private sharedService: SharedService, private router: Router) { }
  Users :any = [];
  ngOnInit(): void {
    this.load()
  }

  remove(userId: any): void {
    this.sharedService.delete(userId).subscribe(() => {
      this.load();
      this.router.navigate(['/admin/amine']);
    });
  }

  load(): void {
    this.sharedService.query().subscribe(
      (response: HttpResponse<IAmine[]>): void => {
        this.Users = response.body;
      },
      (error) => {
        console.error(error);
      }
    );
  }

}
