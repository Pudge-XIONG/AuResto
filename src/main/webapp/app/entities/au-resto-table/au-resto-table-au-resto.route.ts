import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AuRestoTableAuRestoComponent } from './au-resto-table-au-resto.component';
import { AuRestoTableAuRestoDetailComponent } from './au-resto-table-au-resto-detail.component';
import { AuRestoTableAuRestoPopupComponent } from './au-resto-table-au-resto-dialog.component';
import { AuRestoTableAuRestoDeletePopupComponent } from './au-resto-table-au-resto-delete-dialog.component';

export const auRestoTableRoute: Routes = [
    {
        path: 'au-resto-table-au-resto',
        component: AuRestoTableAuRestoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoTable.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'au-resto-table-au-resto/:id',
        component: AuRestoTableAuRestoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoTable.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auRestoTablePopupRoute: Routes = [
    {
        path: 'au-resto-table-au-resto-new',
        component: AuRestoTableAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoTable.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-table-au-resto/:id/edit',
        component: AuRestoTableAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoTable.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-table-au-resto/:id/delete',
        component: AuRestoTableAuRestoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoTable.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
