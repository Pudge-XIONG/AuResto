import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuRestoSharedModule } from '../../shared';
import {
    AuRestoOrderAuRestoService,
    AuRestoOrderAuRestoPopupService,
    AuRestoOrderAuRestoComponent,
    AuRestoOrderAuRestoDetailComponent,
    AuRestoOrderAuRestoDialogComponent,
    AuRestoOrderAuRestoPopupComponent,
    AuRestoOrderAuRestoDeletePopupComponent,
    AuRestoOrderAuRestoDeleteDialogComponent,
    auRestoOrderRoute,
    auRestoOrderPopupRoute,
    AuRestoOrderAuRestoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...auRestoOrderRoute,
    ...auRestoOrderPopupRoute,
];

@NgModule({
    imports: [
        AuRestoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuRestoOrderAuRestoComponent,
        AuRestoOrderAuRestoDetailComponent,
        AuRestoOrderAuRestoDialogComponent,
        AuRestoOrderAuRestoDeleteDialogComponent,
        AuRestoOrderAuRestoPopupComponent,
        AuRestoOrderAuRestoDeletePopupComponent,
    ],
    entryComponents: [
        AuRestoOrderAuRestoComponent,
        AuRestoOrderAuRestoDialogComponent,
        AuRestoOrderAuRestoPopupComponent,
        AuRestoOrderAuRestoDeleteDialogComponent,
        AuRestoOrderAuRestoDeletePopupComponent,
    ],
    providers: [
        AuRestoOrderAuRestoService,
        AuRestoOrderAuRestoPopupService,
        AuRestoOrderAuRestoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuRestoAuRestoOrderAuRestoModule {}
