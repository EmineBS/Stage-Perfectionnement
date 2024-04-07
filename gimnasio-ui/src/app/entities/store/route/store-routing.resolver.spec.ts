import { TestBed } from '@angular/core/testing';

import { StoreRoutingResolver } from './store-routing.resolver';

describe('StoreRoutingResolver', () => {
  let resolver: StoreRoutingResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(StoreRoutingResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
