import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuRestoSharedModule } from '../../shared';
import {
    AuRestoRestaurantTypeAuRestoService,
    AuRestoRestaurantTypeAuRestoPopupService,
    AuRestoRestaurantTypeAuRestoComponent,
    AuRestoRestaurantTypeAuRestoDetailComponent,
    AuRestoRestaurantTypeAuRestoDialogComponent,
    AuRestoRestaurantTypeAuRestoPopupComponent,
    AuRestoRestaurantTypeAuRestoDeletePopupComponent,
    AuRestoRestaurantTypeAuRestoDeleteDialogComponent,
    auRestoRestaurantTypeRoute,
    auRestoRestaurantTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...auRestoRestaurantTypeRoute,
    ...auRestoRestaurantTypePopupRoute,
];

@NgModule({
    imports: [
        AuRestoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuRestoRestaurantTypeAuRestoComponent,
        AuRestoRestaurantTypeAuRestoDetailComponent,
        AuRestoRestaurantTypeAuRestoDialogComponent,
        AuRestoRestaurantTypeAuRestoDeleteDialogComponent,
        AuRestoRestaurantTypeAuRestoPopupComponent,
        AuRestoRestaurantTypeAuRestoDeletePopupComponent,
    ],
    entryComponents: [
        AuRestoRestaurantTypeAuRestoComponent,
        AuRestoRestaurantTypeAuRestoDialogComponent,
        AuRestoRestaurantTypeAuRestoPopupComponent,
        AuRestoRestaurantTypeAuRestoDeleteDialogComponent,
        AuRestoRestaurantTypeAuRestoDeletePopupComponent,
    ],
    providers: [
        AuRestoRestaurantTypeAuRestoService,
        AuRestoRestaurantTypeAuRestoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuRestoAuRestoRestaurantTypeAuRestoModule {}
