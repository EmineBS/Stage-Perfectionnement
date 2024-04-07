import { TestBed } from '@angular/core/testing';

import { EventsRoutingResolver } from './events-routing.resolver';

describe('EventsRoutingResolver', () => {
  let resolver: EventsRoutingResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(EventsRoutingResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
