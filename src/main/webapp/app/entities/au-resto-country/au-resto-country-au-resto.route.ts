import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AuRestoCountryAuRestoComponent } from './au-resto-country-au-resto.component';
import { AuRestoCountryAuRestoDetailComponent } from './au-resto-country-au-resto-detail.component';
import { AuRestoCountryAuRestoPopupComponent } from './au-resto-country-au-resto-dialog.component';
import { AuRestoCountryAuRestoDeletePopupComponent } from './au-resto-country-au-resto-delete-dialog.component';

export const auRestoCountryRoute: Routes = [
    {
        path: 'au-resto-country-au-resto',
        component: AuRestoCountryAuRestoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoCountry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'au-resto-country-au-resto/:id',
        component: AuRestoCountryAuRestoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoCountry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auRestoCountryPopupRoute: Routes = [
    {
        path: 'au-resto-country-au-resto-new',
        component: AuRestoCountryAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoCountry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-country-au-resto/:id/edit',
        component: AuRestoCountryAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoCountry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-country-au-resto/:id/delete',
        component: AuRestoCountryAuRestoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoCountry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
