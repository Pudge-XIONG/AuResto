import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AuRestoReservationAuRestoComponent } from './au-resto-reservation-au-resto.component';
import { AuRestoReservationAuRestoDetailComponent } from './au-resto-reservation-au-resto-detail.component';
import { AuRestoReservationAuRestoPopupComponent } from './au-resto-reservation-au-resto-dialog.component';
import { AuRestoReservationAuRestoDeletePopupComponent } from './au-resto-reservation-au-resto-delete-dialog.component';

@Injectable()
export class AuRestoReservationAuRestoResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const auRestoReservationRoute: Routes = [
    {
        path: 'au-resto-reservation-au-resto',
        component: AuRestoReservationAuRestoComponent,
        resolve: {
            'pagingParams': AuRestoReservationAuRestoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoReservation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'au-resto-reservation-au-resto/:id',
        component: AuRestoReservationAuRestoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoReservation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auRestoReservationPopupRoute: Routes = [
    {
        path: 'au-resto-reservation-au-resto-new',
        component: AuRestoReservationAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoReservation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-reservation-au-resto/:id/edit',
        component: AuRestoReservationAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoReservation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-reservation-au-resto/:id/delete',
        component: AuRestoReservationAuRestoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoReservation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
