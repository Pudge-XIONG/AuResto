import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuRestoSharedModule } from '../../shared';
import {
    AuRestoRecipeAuRestoService,
    AuRestoRecipeAuRestoPopupService,
    AuRestoRecipeAuRestoComponent,
    AuRestoRecipeAuRestoDetailComponent,
    AuRestoRecipeAuRestoDialogComponent,
    AuRestoRecipeAuRestoPopupComponent,
    AuRestoRecipeAuRestoDeletePopupComponent,
    AuRestoRecipeAuRestoDeleteDialogComponent,
    auRestoRecipeRoute,
    auRestoRecipePopupRoute,
} from './';

const ENTITY_STATES = [
    ...auRestoRecipeRoute,
    ...auRestoRecipePopupRoute,
];

@NgModule({
    imports: [
        AuRestoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuRestoRecipeAuRestoComponent,
        AuRestoRecipeAuRestoDetailComponent,
        AuRestoRecipeAuRestoDialogComponent,
        AuRestoRecipeAuRestoDeleteDialogComponent,
        AuRestoRecipeAuRestoPopupComponent,
        AuRestoRecipeAuRestoDeletePopupComponent,
    ],
    entryComponents: [
        AuRestoRecipeAuRestoComponent,
        AuRestoRecipeAuRestoDialogComponent,
        AuRestoRecipeAuRestoPopupComponent,
        AuRestoRecipeAuRestoDeleteDialogComponent,
        AuRestoRecipeAuRestoDeletePopupComponent,
    ],
    providers: [
        AuRestoRecipeAuRestoService,
        AuRestoRecipeAuRestoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuRestoAuRestoRecipeAuRestoModule {}
