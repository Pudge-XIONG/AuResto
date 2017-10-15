import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AuRestoFormulaAuRestoComponent } from './au-resto-formula-au-resto.component';
import { AuRestoFormulaAuRestoDetailComponent } from './au-resto-formula-au-resto-detail.component';
import { AuRestoFormulaAuRestoPopupComponent } from './au-resto-formula-au-resto-dialog.component';
import { AuRestoFormulaAuRestoDeletePopupComponent } from './au-resto-formula-au-resto-delete-dialog.component';

export const auRestoFormulaRoute: Routes = [
    {
        path: 'au-resto-formula-au-resto',
        component: AuRestoFormulaAuRestoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoFormula.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'au-resto-formula-au-resto/:id',
        component: AuRestoFormulaAuRestoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoFormula.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auRestoFormulaPopupRoute: Routes = [
    {
        path: 'au-resto-formula-au-resto-new',
        component: AuRestoFormulaAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoFormula.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-formula-au-resto/:id/edit',
        component: AuRestoFormulaAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoFormula.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-formula-au-resto/:id/delete',
        component: AuRestoFormulaAuRestoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoFormula.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
