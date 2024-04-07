import { TestBed } from '@angular/core/testing';

import { AmineRoutingResolveService } from './amine-routing-resolver.service';

describe('AmineResolver', () => {
  let resolver: AmineRoutingResolveService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(AmineRoutingResolveService);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
