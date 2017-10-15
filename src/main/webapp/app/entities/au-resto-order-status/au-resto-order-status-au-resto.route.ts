import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AuRestoOrderStatusAuRestoComponent } from './au-resto-order-status-au-resto.component';
import { AuRestoOrderStatusAuRestoDetailComponent } from './au-resto-order-status-au-resto-detail.component';
import { AuRestoOrderStatusAuRestoPopupComponent } from './au-resto-order-status-au-resto-dialog.component';
import { AuRestoOrderStatusAuRestoDeletePopupComponent } from './au-resto-order-status-au-resto-delete-dialog.component';

export const auRestoOrderStatusRoute: Routes = [
    {
        path: 'au-resto-order-status-au-resto',
        component: AuRestoOrderStatusAuRestoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoOrderStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'au-resto-order-status-au-resto/:id',
        component: AuRestoOrderStatusAuRestoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoOrderStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auRestoOrderStatusPopupRoute: Routes = [
    {
        path: 'au-resto-order-status-au-resto-new',
        component: AuRestoOrderStatusAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoOrderStatus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-order-status-au-resto/:id/edit',
        component: AuRestoOrderStatusAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoOrderStatus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-order-status-au-resto/:id/delete',
        component: AuRestoOrderStatusAuRestoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoOrderStatus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
