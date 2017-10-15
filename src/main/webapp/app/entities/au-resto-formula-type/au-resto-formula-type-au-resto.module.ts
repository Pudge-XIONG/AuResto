import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuRestoSharedModule } from '../../shared';
import {
    AuRestoFormulaTypeAuRestoService,
    AuRestoFormulaTypeAuRestoPopupService,
    AuRestoFormulaTypeAuRestoComponent,
    AuRestoFormulaTypeAuRestoDetailComponent,
    AuRestoFormulaTypeAuRestoDialogComponent,
    AuRestoFormulaTypeAuRestoPopupComponent,
    AuRestoFormulaTypeAuRestoDeletePopupComponent,
    AuRestoFormulaTypeAuRestoDeleteDialogComponent,
    auRestoFormulaTypeRoute,
    auRestoFormulaTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...auRestoFormulaTypeRoute,
    ...auRestoFormulaTypePopupRoute,
];

@NgModule({
    imports: [
        AuRestoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuRestoFormulaTypeAuRestoComponent,
        AuRestoFormulaTypeAuRestoDetailComponent,
        AuRestoFormulaTypeAuRestoDialogComponent,
        AuRestoFormulaTypeAuRestoDeleteDialogComponent,
        AuRestoFormulaTypeAuRestoPopupComponent,
        AuRestoFormulaTypeAuRestoDeletePopupComponent,
    ],
    entryComponents: [
        AuRestoFormulaTypeAuRestoComponent,
        AuRestoFormulaTypeAuRestoDialogComponent,
        AuRestoFormulaTypeAuRestoPopupComponent,
        AuRestoFormulaTypeAuRestoDeleteDialogComponent,
        AuRestoFormulaTypeAuRestoDeletePopupComponent,
    ],
    providers: [
        AuRestoFormulaTypeAuRestoService,
        AuRestoFormulaTypeAuRestoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuRestoAuRestoFormulaTypeAuRestoModule {}
