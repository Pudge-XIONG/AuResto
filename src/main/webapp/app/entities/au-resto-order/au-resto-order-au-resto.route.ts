import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AuRestoOrderAuRestoComponent } from './au-resto-order-au-resto.component';
import { AuRestoOrderAuRestoDetailComponent } from './au-resto-order-au-resto-detail.component';
import { AuRestoOrderAuRestoPopupComponent } from './au-resto-order-au-resto-dialog.component';
import { AuRestoOrderAuRestoDeletePopupComponent } from './au-resto-order-au-resto-delete-dialog.component';

@Injectable()
export class AuRestoOrderAuRestoResolvePagingParams implements Resolve<any> {

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

export const auRestoOrderRoute: Routes = [
    {
        path: 'au-resto-order-au-resto',
        component: AuRestoOrderAuRestoComponent,
        resolve: {
            'pagingParams': AuRestoOrderAuRestoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'au-resto-order-au-resto/:id',
        component: AuRestoOrderAuRestoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auRestoOrderPopupRoute: Routes = [
    {
        path: 'au-resto-order-au-resto-new',
        component: AuRestoOrderAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoOrder.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-order-au-resto/:id/edit',
        component: AuRestoOrderAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoOrder.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-order-au-resto/:id/delete',
        component: AuRestoOrderAuRestoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoOrder.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
