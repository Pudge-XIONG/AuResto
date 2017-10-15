import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuRestoSharedModule } from '../../shared';
import {
    AuRestoGenderAuRestoService,
    AuRestoGenderAuRestoPopupService,
    AuRestoGenderAuRestoComponent,
    AuRestoGenderAuRestoDetailComponent,
    AuRestoGenderAuRestoDialogComponent,
    AuRestoGenderAuRestoPopupComponent,
    AuRestoGenderAuRestoDeletePopupComponent,
    AuRestoGenderAuRestoDeleteDialogComponent,
    auRestoGenderRoute,
    auRestoGenderPopupRoute,
} from './';

const ENTITY_STATES = [
    ...auRestoGenderRoute,
    ...auRestoGenderPopupRoute,
];

@NgModule({
    imports: [
        AuRestoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuRestoGenderAuRestoComponent,
        AuRestoGenderAuRestoDetailComponent,
        AuRestoGenderAuRestoDialogComponent,
        AuRestoGenderAuRestoDeleteDialogComponent,
        AuRestoGenderAuRestoPopupComponent,
        AuRestoGenderAuRestoDeletePopupComponent,
    ],
    entryComponents: [
        AuRestoGenderAuRestoComponent,
        AuRestoGenderAuRestoDialogComponent,
        AuRestoGenderAuRestoPopupComponent,
        AuRestoGenderAuRestoDeleteDialogComponent,
        AuRestoGenderAuRestoDeletePopupComponent,
    ],
    providers: [
        AuRestoGenderAuRestoService,
        AuRestoGenderAuRestoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuRestoAuRestoGenderAuRestoModule {}
