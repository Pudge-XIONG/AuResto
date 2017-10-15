import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AuRestoBillStatusAuRestoComponent } from './au-resto-bill-status-au-resto.component';
import { AuRestoBillStatusAuRestoDetailComponent } from './au-resto-bill-status-au-resto-detail.component';
import { AuRestoBillStatusAuRestoPopupComponent } from './au-resto-bill-status-au-resto-dialog.component';
import { AuRestoBillStatusAuRestoDeletePopupComponent } from './au-resto-bill-status-au-resto-delete-dialog.component';

export const auRestoBillStatusRoute: Routes = [
    {
        path: 'au-resto-bill-status-au-resto',
        component: AuRestoBillStatusAuRestoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoBillStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'au-resto-bill-status-au-resto/:id',
        component: AuRestoBillStatusAuRestoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoBillStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auRestoBillStatusPopupRoute: Routes = [
    {
        path: 'au-resto-bill-status-au-resto-new',
        component: AuRestoBillStatusAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoBillStatus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-bill-status-au-resto/:id/edit',
        component: AuRestoBillStatusAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoBillStatus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-bill-status-au-resto/:id/delete',
        component: AuRestoBillStatusAuRestoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoBillStatus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
