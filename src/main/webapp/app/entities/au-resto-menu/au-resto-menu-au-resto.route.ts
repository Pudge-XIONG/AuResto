import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AuRestoMenuAuRestoComponent } from './au-resto-menu-au-resto.component';
import { AuRestoMenuAuRestoDetailComponent } from './au-resto-menu-au-resto-detail.component';
import { AuRestoMenuAuRestoPopupComponent } from './au-resto-menu-au-resto-dialog.component';
import { AuRestoMenuAuRestoDeletePopupComponent } from './au-resto-menu-au-resto-delete-dialog.component';

export const auRestoMenuRoute: Routes = [
    {
        path: 'au-resto-menu-au-resto',
        component: AuRestoMenuAuRestoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoMenu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'au-resto-menu-au-resto/:id',
        component: AuRestoMenuAuRestoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoMenu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auRestoMenuPopupRoute: Routes = [
    {
        path: 'au-resto-menu-au-resto-new',
        component: AuRestoMenuAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoMenu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-menu-au-resto/:id/edit',
        component: AuRestoMenuAuRestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoMenu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'au-resto-menu-au-resto/:id/delete',
        component: AuRestoMenuAuRestoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auRestoApp.auRestoMenu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
