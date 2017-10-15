import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuRestoSharedModule } from '../../shared';
import {
    AuRestoTableAuRestoService,
    AuRestoTableAuRestoPopupService,
    AuRestoTableAuRestoComponent,
    AuRestoTableAuRestoDetailComponent,
    AuRestoTableAuRestoDialogComponent,
    AuRestoTableAuRestoPopupComponent,
    AuRestoTableAuRestoDeletePopupComponent,
    AuRestoTableAuRestoDeleteDialogComponent,
    auRestoTableRoute,
    auRestoTablePopupRoute,
} from './';

const ENTITY_STATES = [
    ...auRestoTableRoute,
    ...auRestoTablePopupRoute,
];

@NgModule({
    imports: [
        AuRestoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuRestoTableAuRestoComponent,
        AuRestoTableAuRestoDetailComponent,
        AuRestoTableAuRestoDialogComponent,
        AuRestoTableAuRestoDeleteDialogComponent,
        AuRestoTableAuRestoPopupComponent,
        AuRestoTableAuRestoDeletePopupComponent,
    ],
    entryComponents: [
        AuRestoTableAuRestoComponent,
        AuRestoTableAuRestoDialogComponent,
        AuRestoTableAuRestoPopupComponent,
        AuRestoTableAuRestoDeleteDialogComponent,
        AuRestoTableAuRestoDeletePopupComponent,
    ],
    providers: [
        AuRestoTableAuRestoService,
        AuRestoTableAuRestoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuRestoAuRestoTableAuRestoModule {}
