import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IBadge } from 'app/entities/badge/badge.model';
import { BadgeService } from 'app/entities/badge/service/badge.service';
@Component({
  selector: 'jhi-profile-badge',
  templateUrl: './profile-badge.component.html',
  styleUrls: ['./profile-badge.component.scss'],
})
export class ProfileBadgeComponent implements OnInit {
  badge: IBadge | null = null;

  isSaving = false;
  id: number;

  edit = false;

  constructor(protected badgeService: BadgeService, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.reloadCurrentRoute();
  }

  previousState(): void {
    window.history.back();
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  reloadCurrentRoute() {
    this.badgeService.querySingleAccount().subscribe(badge => {
      this.badge = badge.body;
    });
  }

  getBadgeClass(statusState: string): string {
    if (statusState === 'inactif') {
      return 'bg-danger';
    }
    return 'bg-success';
  }

  formatId(id: number): string {
    return id.toString().padStart(4, '0');
  }
}
