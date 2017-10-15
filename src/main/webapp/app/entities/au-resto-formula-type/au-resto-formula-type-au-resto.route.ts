import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AuRestoFormulaTypeAuRestoComponent } from './au-resto-formula-type-au-resto.component';
import { AuRestoFormulaTypeAuRestoDetailComponent } from './au-resto-formula-type-au-resto-detail.component';
import { AuRestoFormulaTypeAuRestoPopupComponent } from './au-resto-formula-type-au-resto-dialog.component';
import { AuRestoFormulaTypeAuRestoDeletePopupComponent } from './au-resto-formula-type-au-resto-delete-dialog.component';

export const auRestoFormulaTypeRoute: Routes = [
    {
        path: 'au-resto-formula-type-au-resto',
        component: AuRestoFormulaTypeAuRestoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoFormulaType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'au-resto-formula-type-au-resto/:id',
        component: AuRestoFormulaTypeAuRestoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoFormulaType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auRestoFormulaTypePopupRoute: Routes = [
    {
        path: 'au-resto-formula-type-au-resto-new',
        component: AuRestoFormulaTypeAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoFormulaType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-formula-type-au-resto/:id/edit',
        component: AuRestoFormulaTypeAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoFormulaType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-formula-type-au-resto/:id/delete',
        component: AuRestoFormulaTypeAuRestoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoFormulaType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
