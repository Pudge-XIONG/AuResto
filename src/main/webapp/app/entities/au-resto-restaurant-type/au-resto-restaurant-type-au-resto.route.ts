import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AuRestoRestaurantTypeAuRestoComponent } from './au-resto-restaurant-type-au-resto.component';
import { AuRestoRestaurantTypeAuRestoDetailComponent } from './au-resto-restaurant-type-au-resto-detail.component';
import { AuRestoRestaurantTypeAuRestoPopupComponent } from './au-resto-restaurant-type-au-resto-dialog.component';
import {
    AuRestoRestaurantTypeAuRestoDeletePopupComponent
} from './au-resto-restaurant-type-au-resto-delete-dialog.component';

export const auRestoRestaurantTypeRoute: Routes = [
    {
        path: 'au-resto-restaurant-type-au-resto',
        component: AuRestoRestaurantTypeAuRestoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoRestaurantType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'au-resto-restaurant-type-au-resto/:id',
        component: AuRestoRestaurantTypeAuRestoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoRestaurantType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auRestoRestaurantTypePopupRoute: Routes = [
    {
        path: 'au-resto-restaurant-type-au-resto-new',
        component: AuRestoRestaurantTypeAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoRestaurantType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-restaurant-type-au-resto/:id/edit',
        component: AuRestoRestaurantTypeAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoRestaurantType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-restaurant-type-au-resto/:id/delete',
        component: AuRestoRestaurantTypeAuRestoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoRestaurantType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
