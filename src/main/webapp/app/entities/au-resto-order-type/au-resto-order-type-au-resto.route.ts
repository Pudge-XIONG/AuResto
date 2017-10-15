import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AuRestoOrderTypeAuRestoComponent } from './au-resto-order-type-au-resto.component';
import { AuRestoOrderTypeAuRestoDetailComponent } from './au-resto-order-type-au-resto-detail.component';
import { AuRestoOrderTypeAuRestoPopupComponent } from './au-resto-order-type-au-resto-dialog.component';
import { AuRestoOrderTypeAuRestoDeletePopupComponent } from './au-resto-order-type-au-resto-delete-dialog.component';

export const auRestoOrderTypeRoute: Routes = [
    {
        path: 'au-resto-order-type-au-resto',
        component: AuRestoOrderTypeAuRestoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoOrderType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'au-resto-order-type-au-resto/:id',
        component: AuRestoOrderTypeAuRestoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoOrderType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auRestoOrderTypePopupRoute: Routes = [
    {
        path: 'au-resto-order-type-au-resto-new',
        component: AuRestoOrderTypeAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoOrderType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-order-type-au-resto/:id/edit',
        component: AuRestoOrderTypeAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoOrderType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-order-type-au-resto/:id/delete',
        component: AuRestoOrderTypeAuRestoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoOrderType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
