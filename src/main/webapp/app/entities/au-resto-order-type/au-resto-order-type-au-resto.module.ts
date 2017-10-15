import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuRestoSharedModule } from '../../shared';
import {
    AuRestoOrderTypeAuRestoService,
    AuRestoOrderTypeAuRestoPopupService,
    AuRestoOrderTypeAuRestoComponent,
    AuRestoOrderTypeAuRestoDetailComponent,
    AuRestoOrderTypeAuRestoDialogComponent,
    AuRestoOrderTypeAuRestoPopupComponent,
    AuRestoOrderTypeAuRestoDeletePopupComponent,
    AuRestoOrderTypeAuRestoDeleteDialogComponent,
    auRestoOrderTypeRoute,
    auRestoOrderTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...auRestoOrderTypeRoute,
    ...auRestoOrderTypePopupRoute,
];

@NgModule({
    imports: [
        AuRestoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuRestoOrderTypeAuRestoComponent,
        AuRestoOrderTypeAuRestoDetailComponent,
        AuRestoOrderTypeAuRestoDialogComponent,
        AuRestoOrderTypeAuRestoDeleteDialogComponent,
        AuRestoOrderTypeAuRestoPopupComponent,
        AuRestoOrderTypeAuRestoDeletePopupComponent,
    ],
    entryComponents: [
        AuRestoOrderTypeAuRestoComponent,
        AuRestoOrderTypeAuRestoDialogComponent,
        AuRestoOrderTypeAuRestoPopupComponent,
        AuRestoOrderTypeAuRestoDeleteDialogComponent,
        AuRestoOrderTypeAuRestoDeletePopupComponent,
    ],
    providers: [
        AuRestoOrderTypeAuRestoService,
        AuRestoOrderTypeAuRestoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuRestoAuRestoOrderTypeAuRestoModule {}
