import { TestBed } from '@angular/core/testing';

import { GameRoutingResolveService } from './game-routing-resolve.service';

describe('GameRoutingResolveService', () => {
  let service: GameRoutingResolveService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GameRoutingResolveService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
