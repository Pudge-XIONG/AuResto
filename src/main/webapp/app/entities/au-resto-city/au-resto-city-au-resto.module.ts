import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuRestoSharedModule } from '../../shared';
import {
    AuRestoCityAuRestoService,
    AuRestoCityAuRestoPopupService,
    AuRestoCityAuRestoComponent,
    AuRestoCityAuRestoDetailComponent,
    AuRestoCityAuRestoDialogComponent,
    AuRestoCityAuRestoPopupComponent,
    AuRestoCityAuRestoDeletePopupComponent,
    AuRestoCityAuRestoDeleteDialogComponent,
    auRestoCityRoute,
    auRestoCityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...auRestoCityRoute,
    ...auRestoCityPopupRoute,
];

@NgModule({
    imports: [
        AuRestoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuRestoCityAuRestoComponent,
        AuRestoCityAuRestoDetailComponent,
        AuRestoCityAuRestoDialogComponent,
        AuRestoCityAuRestoDeleteDialogComponent,
        AuRestoCityAuRestoPopupComponent,
        AuRestoCityAuRestoDeletePopupComponent,
    ],
    entryComponents: [
        AuRestoCityAuRestoComponent,
        AuRestoCityAuRestoDialogComponent,
        AuRestoCityAuRestoPopupComponent,
        AuRestoCityAuRestoDeleteDialogComponent,
        AuRestoCityAuRestoDeletePopupComponent,
    ],
    providers: [
        AuRestoCityAuRestoService,
        AuRestoCityAuRestoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuRestoAuRestoCityAuRestoModule {}
