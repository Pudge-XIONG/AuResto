import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuRestoSharedModule } from '../../shared';
import {
    AuRestoRecipeTypeAuRestoService,
    AuRestoRecipeTypeAuRestoPopupService,
    AuRestoRecipeTypeAuRestoComponent,
    AuRestoRecipeTypeAuRestoDetailComponent,
    AuRestoRecipeTypeAuRestoDialogComponent,
    AuRestoRecipeTypeAuRestoPopupComponent,
    AuRestoRecipeTypeAuRestoDeletePopupComponent,
    AuRestoRecipeTypeAuRestoDeleteDialogComponent,
    auRestoRecipeTypeRoute,
    auRestoRecipeTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...auRestoRecipeTypeRoute,
    ...auRestoRecipeTypePopupRoute,
];

@NgModule({
    imports: [
        AuRestoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuRestoRecipeTypeAuRestoComponent,
        AuRestoRecipeTypeAuRestoDetailComponent,
        AuRestoRecipeTypeAuRestoDialogComponent,
        AuRestoRecipeTypeAuRestoDeleteDialogComponent,
        AuRestoRecipeTypeAuRestoPopupComponent,
        AuRestoRecipeTypeAuRestoDeletePopupComponent,
    ],
    entryComponents: [
        AuRestoRecipeTypeAuRestoComponent,
        AuRestoRecipeTypeAuRestoDialogComponent,
        AuRestoRecipeTypeAuRestoPopupComponent,
        AuRestoRecipeTypeAuRestoDeleteDialogComponent,
        AuRestoRecipeTypeAuRestoDeletePopupComponent,
    ],
    providers: [
        AuRestoRecipeTypeAuRestoService,
        AuRestoRecipeTypeAuRestoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuRestoAuRestoRecipeTypeAuRestoModule {}
