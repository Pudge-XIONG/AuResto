import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuRestoSharedModule } from '../../shared';
import {
    AuRestoCountryAuRestoService,
    AuRestoCountryAuRestoPopupService,
    AuRestoCountryAuRestoComponent,
    AuRestoCountryAuRestoDetailComponent,
    AuRestoCountryAuRestoDialogComponent,
    AuRestoCountryAuRestoPopupComponent,
    AuRestoCountryAuRestoDeletePopupComponent,
    AuRestoCountryAuRestoDeleteDialogComponent,
    auRestoCountryRoute,
    auRestoCountryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...auRestoCountryRoute,
    ...auRestoCountryPopupRoute,
];

@NgModule({
    imports: [
        AuRestoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuRestoCountryAuRestoComponent,
        AuRestoCountryAuRestoDetailComponent,
        AuRestoCountryAuRestoDialogComponent,
        AuRestoCountryAuRestoDeleteDialogComponent,
        AuRestoCountryAuRestoPopupComponent,
        AuRestoCountryAuRestoDeletePopupComponent,
    ],
    entryComponents: [
        AuRestoCountryAuRestoComponent,
        AuRestoCountryAuRestoDialogComponent,
        AuRestoCountryAuRestoPopupComponent,
        AuRestoCountryAuRestoDeleteDialogComponent,
        AuRestoCountryAuRestoDeletePopupComponent,
    ],
    providers: [
        AuRestoCountryAuRestoService,
        AuRestoCountryAuRestoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuRestoAuRestoCountryAuRestoModule {}
