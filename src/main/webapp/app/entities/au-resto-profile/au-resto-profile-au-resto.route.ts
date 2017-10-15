import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AuRestoProfileAuRestoComponent } from './au-resto-profile-au-resto.component';
import { AuRestoProfileAuRestoDetailComponent } from './au-resto-profile-au-resto-detail.component';
import { AuRestoProfileAuRestoPopupComponent } from './au-resto-profile-au-resto-dialog.component';
import { AuRestoProfileAuRestoDeletePopupComponent } from './au-resto-profile-au-resto-delete-dialog.component';

export const auRestoProfileRoute: Routes = [
    {
        path: 'au-resto-profile-au-resto',
        component: AuRestoProfileAuRestoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoProfile.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'au-resto-profile-au-resto/:id',
        component: AuRestoProfileAuRestoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoProfile.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auRestoProfilePopupRoute: Routes = [
    {
        path: 'au-resto-profile-au-resto-new',
        component: AuRestoProfileAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoProfile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-profile-au-resto/:id/edit',
        component: AuRestoProfileAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoProfile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-profile-au-resto/:id/delete',
        component: AuRestoProfileAuRestoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoProfile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
