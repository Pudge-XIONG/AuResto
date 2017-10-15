import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuRestoSharedModule } from '../../shared';
import {
    AuRestoRestaurantAuRestoService,
    AuRestoRestaurantAuRestoPopupService,
    AuRestoRestaurantAuRestoComponent,
    AuRestoRestaurantAuRestoDetailComponent,
    AuRestoRestaurantAuRestoDialogComponent,
    AuRestoRestaurantAuRestoPopupComponent,
    AuRestoRestaurantAuRestoDeletePopupComponent,
    AuRestoRestaurantAuRestoDeleteDialogComponent,
    auRestoRestaurantRoute,
    auRestoRestaurantPopupRoute,
} from './';

const ENTITY_STATES = [
    ...auRestoRestaurantRoute,
    ...auRestoRestaurantPopupRoute,
];

@NgModule({
    imports: [
        AuRestoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuRestoRestaurantAuRestoComponent,
        AuRestoRestaurantAuRestoDetailComponent,
        AuRestoRestaurantAuRestoDialogComponent,
        AuRestoRestaurantAuRestoDeleteDialogComponent,
        AuRestoRestaurantAuRestoPopupComponent,
        AuRestoRestaurantAuRestoDeletePopupComponent,
    ],
    entryComponents: [
        AuRestoRestaurantAuRestoComponent,
        AuRestoRestaurantAuRestoDialogComponent,
        AuRestoRestaurantAuRestoPopupComponent,
        AuRestoRestaurantAuRestoDeleteDialogComponent,
        AuRestoRestaurantAuRestoDeletePopupComponent,
    ],
    providers: [
        AuRestoRestaurantAuRestoService,
        AuRestoRestaurantAuRestoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuRestoAuRestoRestaurantAuRestoModule {}
