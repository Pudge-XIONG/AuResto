import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AuRestoBillAuRestoComponent } from './au-resto-bill-au-resto.component';
import { AuRestoBillAuRestoDetailComponent } from './au-resto-bill-au-resto-detail.component';
import { AuRestoBillAuRestoPopupComponent } from './au-resto-bill-au-resto-dialog.component';
import { AuRestoBillAuRestoDeletePopupComponent } from './au-resto-bill-au-resto-delete-dialog.component';

@Injectable()
export class AuRestoBillAuRestoResolvePagingParams implements Resolve<any> {

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

export const auRestoBillRoute: Routes = [
    {
        path: 'au-resto-bill-au-resto',
        component: AuRestoBillAuRestoComponent,
        resolve: {
            'pagingParams': AuRestoBillAuRestoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoBill.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'au-resto-bill-au-resto/:id',
        component: AuRestoBillAuRestoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoBill.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auRestoBillPopupRoute: Routes = [
    {
        path: 'au-resto-bill-au-resto-new',
        component: AuRestoBillAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoBill.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-bill-au-resto/:id/edit',
        component: AuRestoBillAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoBill.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-bill-au-resto/:id/delete',
        component: AuRestoBillAuRestoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoBill.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
