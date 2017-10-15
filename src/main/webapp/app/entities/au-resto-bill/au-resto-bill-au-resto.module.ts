import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuRestoSharedModule } from '../../shared';
import {
    AuRestoBillAuRestoService,
    AuRestoBillAuRestoPopupService,
    AuRestoBillAuRestoComponent,
    AuRestoBillAuRestoDetailComponent,
    AuRestoBillAuRestoDialogComponent,
    AuRestoBillAuRestoPopupComponent,
    AuRestoBillAuRestoDeletePopupComponent,
    AuRestoBillAuRestoDeleteDialogComponent,
    auRestoBillRoute,
    auRestoBillPopupRoute,
    AuRestoBillAuRestoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...auRestoBillRoute,
    ...auRestoBillPopupRoute,
];

@NgModule({
    imports: [
        AuRestoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuRestoBillAuRestoComponent,
        AuRestoBillAuRestoDetailComponent,
        AuRestoBillAuRestoDialogComponent,
        AuRestoBillAuRestoDeleteDialogComponent,
        AuRestoBillAuRestoPopupComponent,
        AuRestoBillAuRestoDeletePopupComponent,
    ],
    entryComponents: [
        AuRestoBillAuRestoComponent,
        AuRestoBillAuRestoDialogComponent,
        AuRestoBillAuRestoPopupComponent,
        AuRestoBillAuRestoDeleteDialogComponent,
        AuRestoBillAuRestoDeletePopupComponent,
    ],
    providers: [
        AuRestoBillAuRestoService,
        AuRestoBillAuRestoPopupService,
        AuRestoBillAuRestoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuRestoAuRestoBillAuRestoModule {}
