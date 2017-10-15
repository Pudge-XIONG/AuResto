import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AuRestoProvinceAuRestoComponent } from './au-resto-province-au-resto.component';
import { AuRestoProvinceAuRestoDetailComponent } from './au-resto-province-au-resto-detail.component';
import { AuRestoProvinceAuRestoPopupComponent } from './au-resto-province-au-resto-dialog.component';
import { AuRestoProvinceAuRestoDeletePopupComponent } from './au-resto-province-au-resto-delete-dialog.component';

export const auRestoProvinceRoute: Routes = [
    {
        path: 'au-resto-province-au-resto',
        component: AuRestoProvinceAuRestoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoProvince.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'au-resto-province-au-resto/:id',
        component: AuRestoProvinceAuRestoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoProvince.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auRestoProvincePopupRoute: Routes = [
    {
        path: 'au-resto-province-au-resto-new',
        component: AuRestoProvinceAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoProvince.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-province-au-resto/:id/edit',
        component: AuRestoProvinceAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoProvince.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-province-au-resto/:id/delete',
        component: AuRestoProvinceAuRestoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoProvince.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
