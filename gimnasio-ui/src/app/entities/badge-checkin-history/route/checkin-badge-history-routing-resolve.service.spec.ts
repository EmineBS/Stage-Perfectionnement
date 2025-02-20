import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IProfile } from 'app/entities/profile/profile.model';
import { ProfileService } from 'app/entities/profile/service/profile.service';

import { CheckinBadgeHistoryComponentRoutingResolveService } from './checkin-badge-history-routing-resolve.service';
import { IBadgeCheckinBadgeHistoryComponent } from 'app/entities/badge/badge.model';

describe('Profile routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: CheckinBadgeHistoryComponentRoutingResolveService;
  let service: ProfileService;
  let resultCheckinBadgeHistoryComponent: IBadgeCheckinBadgeHistoryComponent | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(CheckinBadgeHistoryComponentRoutingResolveService);
    service = TestBed.inject(ProfileService);
    resultCheckinBadgeHistoryComponent = undefined;
  });

  describe('resolve', () => {
    it('should return IProfile returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCheckinBadgeHistoryComponent = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCheckinBadgeHistoryComponent).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCheckinBadgeHistoryComponent = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCheckinBadgeHistoryComponent).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IProfile>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCheckinBadgeHistoryComponent = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCheckinBadgeHistoryComponent).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
