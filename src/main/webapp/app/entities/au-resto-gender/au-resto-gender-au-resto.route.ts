import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AuRestoGenderAuRestoComponent } from './au-resto-gender-au-resto.component';
import { AuRestoGenderAuRestoDetailComponent } from './au-resto-gender-au-resto-detail.component';
import { AuRestoGenderAuRestoPopupComponent } from './au-resto-gender-au-resto-dialog.component';
import { AuRestoGenderAuRestoDeletePopupComponent } from './au-resto-gender-au-resto-delete-dialog.component';

export const auRestoGenderRoute: Routes = [
    {
        path: 'au-resto-gender-au-resto',
        component: AuRestoGenderAuRestoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoGender.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'au-resto-gender-au-resto/:id',
        component: AuRestoGenderAuRestoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoGender.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auRestoGenderPopupRoute: Routes = [
    {
        path: 'au-resto-gender-au-resto-new',
        component: AuRestoGenderAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoGender.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-gender-au-resto/:id/edit',
        component: AuRestoGenderAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoGender.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-gender-au-resto/:id/delete',
        component: AuRestoGenderAuRestoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoGender.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
