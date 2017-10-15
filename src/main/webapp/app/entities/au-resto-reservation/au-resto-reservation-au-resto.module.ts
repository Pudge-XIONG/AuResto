import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuRestoSharedModule } from '../../shared';
import {
    AuRestoReservationAuRestoService,
    AuRestoReservationAuRestoPopupService,
    AuRestoReservationAuRestoComponent,
    AuRestoReservationAuRestoDetailComponent,
    AuRestoReservationAuRestoDialogComponent,
    AuRestoReservationAuRestoPopupComponent,
    AuRestoReservationAuRestoDeletePopupComponent,
    AuRestoReservationAuRestoDeleteDialogComponent,
    auRestoReservationRoute,
    auRestoReservationPopupRoute,
    AuRestoReservationAuRestoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...auRestoReservationRoute,
    ...auRestoReservationPopupRoute,
];

@NgModule({
    imports: [
        AuRestoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuRestoReservationAuRestoComponent,
        AuRestoReservationAuRestoDetailComponent,
        AuRestoReservationAuRestoDialogComponent,
        AuRestoReservationAuRestoDeleteDialogComponent,
        AuRestoReservationAuRestoPopupComponent,
        AuRestoReservationAuRestoDeletePopupComponent,
    ],
    entryComponents: [
        AuRestoReservationAuRestoComponent,
        AuRestoReservationAuRestoDialogComponent,
        AuRestoReservationAuRestoPopupComponent,
        AuRestoReservationAuRestoDeleteDialogComponent,
        AuRestoReservationAuRestoDeletePopupComponent,
    ],
    providers: [
        AuRestoReservationAuRestoService,
        AuRestoReservationAuRestoPopupService,
        AuRestoReservationAuRestoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuRestoAuRestoReservationAuRestoModule {}
