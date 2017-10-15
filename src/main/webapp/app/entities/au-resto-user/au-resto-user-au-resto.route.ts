import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AuRestoUserAuRestoComponent } from './au-resto-user-au-resto.component';
import { AuRestoUserAuRestoDetailComponent } from './au-resto-user-au-resto-detail.component';
import { AuRestoUserAuRestoPopupComponent } from './au-resto-user-au-resto-dialog.component';
import { AuRestoUserAuRestoDeletePopupComponent } from './au-resto-user-au-resto-delete-dialog.component';

@Injectable()
export class AuRestoUserAuRestoResolvePagingParams implements Resolve<any> {

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

export const auRestoUserRoute: Routes = [
    {
        path: 'au-resto-user-au-resto',
        component: AuRestoUserAuRestoComponent,
        resolve: {
            'pagingParams': AuRestoUserAuRestoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'au-resto-user-au-resto/:id',
        component: AuRestoUserAuRestoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auRestoUserPopupRoute: Routes = [
    {
        path: 'au-resto-user-au-resto-new',
        component: AuRestoUserAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-user-au-resto/:id/edit',
        component: AuRestoUserAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-user-au-resto/:id/delete',
        component: AuRestoUserAuRestoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
