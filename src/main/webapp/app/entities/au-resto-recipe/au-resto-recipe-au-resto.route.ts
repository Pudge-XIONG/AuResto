import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AuRestoRecipeAuRestoComponent } from './au-resto-recipe-au-resto.component';
import { AuRestoRecipeAuRestoDetailComponent } from './au-resto-recipe-au-resto-detail.component';
import { AuRestoRecipeAuRestoPopupComponent } from './au-resto-recipe-au-resto-dialog.component';
import { AuRestoRecipeAuRestoDeletePopupComponent } from './au-resto-recipe-au-resto-delete-dialog.component';

export const auRestoRecipeRoute: Routes = [
    {
        path: 'au-resto-recipe-au-resto',
        component: AuRestoRecipeAuRestoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoRecipe.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'au-resto-recipe-au-resto/:id',
        component: AuRestoRecipeAuRestoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoRecipe.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auRestoRecipePopupRoute: Routes = [
    {
        path: 'au-resto-recipe-au-resto-new',
        component: AuRestoRecipeAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoRecipe.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-recipe-au-resto/:id/edit',
        component: AuRestoRecipeAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoRecipe.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-recipe-au-resto/:id/delete',
        component: AuRestoRecipeAuRestoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoRecipe.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
