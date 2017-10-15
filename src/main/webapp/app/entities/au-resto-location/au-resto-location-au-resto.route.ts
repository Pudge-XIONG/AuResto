import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AuRestoLocationAuRestoComponent } from './au-resto-location-au-resto.component';
import { AuRestoLocationAuRestoDetailComponent } from './au-resto-location-au-resto-detail.component';
import { AuRestoLocationAuRestoPopupComponent } from './au-resto-location-au-resto-dialog.component';
import { AuRestoLocationAuRestoDeletePopupComponent } from './au-resto-location-au-resto-delete-dialog.component';

export const auRestoLocationRoute: Routes = [
    {
        path: 'au-resto-location-au-resto',
        component: AuRestoLocationAuRestoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoLocation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'au-resto-location-au-resto/:id',
        component: AuRestoLocationAuRestoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoLocation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auRestoLocationPopupRoute: Routes = [
    {
        path: 'au-resto-location-au-resto-new',
        component: AuRestoLocationAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoLocation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-location-au-resto/:id/edit',
        component: AuRestoLocationAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoLocation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-location-au-resto/:id/delete',
        component: AuRestoLocationAuRestoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoLocation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
