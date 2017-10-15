import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuRestoSharedModule } from '../../shared';
import {
    AuRestoLocationAuRestoService,
    AuRestoLocationAuRestoPopupService,
    AuRestoLocationAuRestoComponent,
    AuRestoLocationAuRestoDetailComponent,
    AuRestoLocationAuRestoDialogComponent,
    AuRestoLocationAuRestoPopupComponent,
    AuRestoLocationAuRestoDeletePopupComponent,
    AuRestoLocationAuRestoDeleteDialogComponent,
    auRestoLocationRoute,
    auRestoLocationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...auRestoLocationRoute,
    ...auRestoLocationPopupRoute,
];

@NgModule({
    imports: [
        AuRestoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuRestoLocationAuRestoComponent,
        AuRestoLocationAuRestoDetailComponent,
        AuRestoLocationAuRestoDialogComponent,
        AuRestoLocationAuRestoDeleteDialogComponent,
        AuRestoLocationAuRestoPopupComponent,
        AuRestoLocationAuRestoDeletePopupComponent,
    ],
    entryComponents: [
        AuRestoLocationAuRestoComponent,
        AuRestoLocationAuRestoDialogComponent,
        AuRestoLocationAuRestoPopupComponent,
        AuRestoLocationAuRestoDeleteDialogComponent,
        AuRestoLocationAuRestoDeletePopupComponent,
    ],
    providers: [
        AuRestoLocationAuRestoService,
        AuRestoLocationAuRestoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuRestoAuRestoLocationAuRestoModule {}
