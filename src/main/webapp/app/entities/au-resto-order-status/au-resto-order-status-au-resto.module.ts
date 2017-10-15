import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuRestoSharedModule } from '../../shared';
import {
    AuRestoOrderStatusAuRestoService,
    AuRestoOrderStatusAuRestoPopupService,
    AuRestoOrderStatusAuRestoComponent,
    AuRestoOrderStatusAuRestoDetailComponent,
    AuRestoOrderStatusAuRestoDialogComponent,
    AuRestoOrderStatusAuRestoPopupComponent,
    AuRestoOrderStatusAuRestoDeletePopupComponent,
    AuRestoOrderStatusAuRestoDeleteDialogComponent,
    auRestoOrderStatusRoute,
    auRestoOrderStatusPopupRoute,
} from './';

const ENTITY_STATES = [
    ...auRestoOrderStatusRoute,
    ...auRestoOrderStatusPopupRoute,
];

@NgModule({
    imports: [
        AuRestoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuRestoOrderStatusAuRestoComponent,
        AuRestoOrderStatusAuRestoDetailComponent,
        AuRestoOrderStatusAuRestoDialogComponent,
        AuRestoOrderStatusAuRestoDeleteDialogComponent,
        AuRestoOrderStatusAuRestoPopupComponent,
        AuRestoOrderStatusAuRestoDeletePopupComponent,
    ],
    entryComponents: [
        AuRestoOrderStatusAuRestoComponent,
        AuRestoOrderStatusAuRestoDialogComponent,
        AuRestoOrderStatusAuRestoPopupComponent,
        AuRestoOrderStatusAuRestoDeleteDialogComponent,
        AuRestoOrderStatusAuRestoDeletePopupComponent,
    ],
    providers: [
        AuRestoOrderStatusAuRestoService,
        AuRestoOrderStatusAuRestoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuRestoAuRestoOrderStatusAuRestoModule {}
