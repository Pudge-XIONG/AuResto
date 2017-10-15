import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AuRestoPhotoAuRestoComponent } from './au-resto-photo-au-resto.component';
import { AuRestoPhotoAuRestoDetailComponent } from './au-resto-photo-au-resto-detail.component';
import { AuRestoPhotoAuRestoPopupComponent } from './au-resto-photo-au-resto-dialog.component';
import { AuRestoPhotoAuRestoDeletePopupComponent } from './au-resto-photo-au-resto-delete-dialog.component';

export const auRestoPhotoRoute: Routes = [
    {
        path: 'au-resto-photo-au-resto',
        component: AuRestoPhotoAuRestoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoPhoto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'au-resto-photo-au-resto/:id',
        component: AuRestoPhotoAuRestoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoPhoto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auRestoPhotoPopupRoute: Routes = [
    {
        path: 'au-resto-photo-au-resto-new',
        component: AuRestoPhotoAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoPhoto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-photo-au-resto/:id/edit',
        component: AuRestoPhotoAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoPhoto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-photo-au-resto/:id/delete',
        component: AuRestoPhotoAuRestoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoPhoto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
