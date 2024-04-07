import { TestBed } from '@angular/core/testing';

import { TournamentRoutingResolve } from './tournament-routing-resolve.service';

describe('TournamentRoutingResolve', () => {
  let resolver: TournamentRoutingResolve;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(TournamentRoutingResolve);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
