import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { IProfile } from 'app/entities/profile/profile.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProfileFormGroup, ProfileFormService } from 'app/entities/profile/update/profile-form.service';

import { ProfileService } from 'app/entities/profile/service/profile.service';
import { Observable, finalize } from 'rxjs';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IBadge } from 'app/entities/badge/badge.model';

@Component({
  selector: 'jhi-profile-detail',
  templateUrl: './profile-detail.component.html',
})
export class ProfileDetailComponent implements OnInit {
  profile: IProfile | null = null;
  badge: IBadge | null = null;
  id: number;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected profileFormService: ProfileFormService,
    protected profilService: ProfileService,
    protected router: Router
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ badge }) => {
      this.id = badge.id;
      this.getProfile(this.id);
    });
  }

  getProfile(id: number) {
    console.log('badge id is ', id);
    this.profilService.findByBadge(id).subscribe(profile => {
      this.profile = profile.body;
    },
    (error: HttpErrorResponse) => {
      // Handle error here
      console.error('An error occurred:', error.error);
    }
    );
  }
}
