import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AuRestoRestaurantAuRestoComponent } from './au-resto-restaurant-au-resto.component';
import { AuRestoRestaurantAuRestoDetailComponent } from './au-resto-restaurant-au-resto-detail.component';
import { AuRestoRestaurantAuRestoPopupComponent } from './au-resto-restaurant-au-resto-dialog.component';
import { AuRestoRestaurantAuRestoDeletePopupComponent } from './au-resto-restaurant-au-resto-delete-dialog.component';

export const auRestoRestaurantRoute: Routes = [
    {
        path: 'au-resto-restaurant-au-resto',
        component: AuRestoRestaurantAuRestoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoRestaurant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'au-resto-restaurant-au-resto/:id',
        component: AuRestoRestaurantAuRestoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoRestaurant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auRestoRestaurantPopupRoute: Routes = [
    {
        path: 'au-resto-restaurant-au-resto-new',
        component: AuRestoRestaurantAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoRestaurant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-restaurant-au-resto/:id/edit',
        component: AuRestoRestaurantAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoRestaurant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-restaurant-au-resto/:id/delete',
        component: AuRestoRestaurantAuRestoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoRestaurant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
