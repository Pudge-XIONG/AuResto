import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AuRestoRecipeTypeAuRestoComponent } from './au-resto-recipe-type-au-resto.component';
import { AuRestoRecipeTypeAuRestoDetailComponent } from './au-resto-recipe-type-au-resto-detail.component';
import { AuRestoRecipeTypeAuRestoPopupComponent } from './au-resto-recipe-type-au-resto-dialog.component';
import { AuRestoRecipeTypeAuRestoDeletePopupComponent } from './au-resto-recipe-type-au-resto-delete-dialog.component';

export const auRestoRecipeTypeRoute: Routes = [
    {
        path: 'au-resto-recipe-type-au-resto',
        component: AuRestoRecipeTypeAuRestoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoRecipeType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'au-resto-recipe-type-au-resto/:id',
        component: AuRestoRecipeTypeAuRestoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoRecipeType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auRestoRecipeTypePopupRoute: Routes = [
    {
        path: 'au-resto-recipe-type-au-resto-new',
        component: AuRestoRecipeTypeAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoRecipeType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-recipe-type-au-resto/:id/edit',
        component: AuRestoRecipeTypeAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoRecipeType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-recipe-type-au-resto/:id/delete',
        component: AuRestoRecipeTypeAuRestoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoRecipeType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
