import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AuRestoCityAuRestoComponent } from './au-resto-city-au-resto.component';
import { AuRestoCityAuRestoDetailComponent } from './au-resto-city-au-resto-detail.component';
import { AuRestoCityAuRestoPopupComponent } from './au-resto-city-au-resto-dialog.component';
import { AuRestoCityAuRestoDeletePopupComponent } from './au-resto-city-au-resto-delete-dialog.component';

export const auRestoCityRoute: Routes = [
    {
        path: 'au-resto-city-au-resto',
        component: AuRestoCityAuRestoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoCity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'au-resto-city-au-resto/:id',
        component: AuRestoCityAuRestoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoCity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auRestoCityPopupRoute: Routes = [
    {
        path: 'au-resto-city-au-resto-new',
        component: AuRestoCityAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoCity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-city-au-resto/:id/edit',
        component: AuRestoCityAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoCity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-city-au-resto/:id/delete',
        component: AuRestoCityAuRestoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoCity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
