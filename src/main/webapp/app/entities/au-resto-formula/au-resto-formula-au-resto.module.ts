import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuRestoSharedModule } from '../../shared';
import {
    AuRestoFormulaAuRestoService,
    AuRestoFormulaAuRestoPopupService,
    AuRestoFormulaAuRestoComponent,
    AuRestoFormulaAuRestoDetailComponent,
    AuRestoFormulaAuRestoDialogComponent,
    AuRestoFormulaAuRestoPopupComponent,
    AuRestoFormulaAuRestoDeletePopupComponent,
    AuRestoFormulaAuRestoDeleteDialogComponent,
    auRestoFormulaRoute,
    auRestoFormulaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...auRestoFormulaRoute,
    ...auRestoFormulaPopupRoute,
];

@NgModule({
    imports: [
        AuRestoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuRestoFormulaAuRestoComponent,
        AuRestoFormulaAuRestoDetailComponent,
        AuRestoFormulaAuRestoDialogComponent,
        AuRestoFormulaAuRestoDeleteDialogComponent,
        AuRestoFormulaAuRestoPopupComponent,
        AuRestoFormulaAuRestoDeletePopupComponent,
    ],
    entryComponents: [
        AuRestoFormulaAuRestoComponent,
        AuRestoFormulaAuRestoDialogComponent,
        AuRestoFormulaAuRestoPopupComponent,
        AuRestoFormulaAuRestoDeleteDialogComponent,
        AuRestoFormulaAuRestoDeletePopupComponent,
    ],
    providers: [
        AuRestoFormulaAuRestoService,
        AuRestoFormulaAuRestoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuRestoAuRestoFormulaAuRestoModule {}
