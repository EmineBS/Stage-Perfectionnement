import { Component, Input, OnInit } from '@angular/core';
import { NgbNavConfig } from '@ng-bootstrap/ng-bootstrap';
import { IGame } from 'app/entities/game/game.model';
import { GameService } from 'app/entities/game/service/game.service';
import { ThemeOptions } from 'app/shared/theme-options';
import { SidebarMenuService } from 'app/layouts/sidebar/sidebar-menu/sidebar-menu.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountService } from 'app/core/auth/account.service';
import { Location } from '@angular/common';
import { SessionStorageService } from 'ngx-webstorage';

@Component({
  selector: 'jhi-header-dots',
  templateUrl: './header-dots.component.html',
  styleUrls: ['./header-dots.component.scss'],
})
export class HeaderDotsComponent implements OnInit {
  games?: IGame[];
  activeGym: IGame;

  @Input()
  gymName: string = "";

  constructor(
    public globals: ThemeOptions,
    config: NgbNavConfig,
    protected gameService: GameService,
    protected SidebarMenuService: SidebarMenuService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    private sessionStorageService: SessionStorageService,
    private location: Location,
  ) {
    config.destroyOnHide = false;
    config.roles = false;
  }

  ngOnInit(): void {
    this.load();
  }

  toggleSidebarMobileOpen() {
    this.globals.toggleSidebarMobile = !this.globals.toggleSidebarMobile;
    this.globals.toggleSidebar = false;
  }

  load(): void {
    this.gameService.query().subscribe(data => {
      if (data.body && data.body.length > 0) {
        this.games = data.body;
      }
    });
  }

  makeGymActive(game: any): void {
    this.sessionStorageService.store('game', game);
    this.gymName = this.sessionStorageService.retrieve('game').gameName;
    //this.location.replaceState('/gyms/' + gym.id + '/packs');
    //location.reload();
    //this.gymName = game.gameName;
    //this.router.navigate(["/tournament"]);
    this.router.navigate(['.'], { relativeTo: this.activatedRoute });
  }
}
