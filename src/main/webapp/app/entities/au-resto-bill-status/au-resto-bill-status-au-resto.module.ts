import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuRestoSharedModule } from '../../shared';
import {
    AuRestoBillStatusAuRestoService,
    AuRestoBillStatusAuRestoPopupService,
    AuRestoBillStatusAuRestoComponent,
    AuRestoBillStatusAuRestoDetailComponent,
    AuRestoBillStatusAuRestoDialogComponent,
    AuRestoBillStatusAuRestoPopupComponent,
    AuRestoBillStatusAuRestoDeletePopupComponent,
    AuRestoBillStatusAuRestoDeleteDialogComponent,
    auRestoBillStatusRoute,
    auRestoBillStatusPopupRoute,
} from './';

const ENTITY_STATES = [
    ...auRestoBillStatusRoute,
    ...auRestoBillStatusPopupRoute,
];

@NgModule({
    imports: [
        AuRestoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuRestoBillStatusAuRestoComponent,
        AuRestoBillStatusAuRestoDetailComponent,
        AuRestoBillStatusAuRestoDialogComponent,
        AuRestoBillStatusAuRestoDeleteDialogComponent,
        AuRestoBillStatusAuRestoPopupComponent,
        AuRestoBillStatusAuRestoDeletePopupComponent,
    ],
    entryComponents: [
        AuRestoBillStatusAuRestoComponent,
        AuRestoBillStatusAuRestoDialogComponent,
        AuRestoBillStatusAuRestoPopupComponent,
        AuRestoBillStatusAuRestoDeleteDialogComponent,
        AuRestoBillStatusAuRestoDeletePopupComponent,
    ],
    providers: [
        AuRestoBillStatusAuRestoService,
        AuRestoBillStatusAuRestoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuRestoAuRestoBillStatusAuRestoModule {}
