import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuRestoSharedModule } from '../../shared';
import {
    AuRestoMenuAuRestoService,
    AuRestoMenuAuRestoPopupService,
    AuRestoMenuAuRestoComponent,
    AuRestoMenuAuRestoDetailComponent,
    AuRestoMenuAuRestoDialogComponent,
    AuRestoMenuAuRestoPopupComponent,
    AuRestoMenuAuRestoDeletePopupComponent,
    AuRestoMenuAuRestoDeleteDialogComponent,
    auRestoMenuRoute,
    auRestoMenuPopupRoute,
} from './';

const ENTITY_STATES = [
    ...auRestoMenuRoute,
    ...auRestoMenuPopupRoute,
];

@NgModule({
    imports: [
        AuRestoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuRestoMenuAuRestoComponent,
        AuRestoMenuAuRestoDetailComponent,
        AuRestoMenuAuRestoDialogComponent,
        AuRestoMenuAuRestoDeleteDialogComponent,
        AuRestoMenuAuRestoPopupComponent,
        AuRestoMenuAuRestoDeletePopupComponent,
    ],
    entryComponents: [
        AuRestoMenuAuRestoComponent,
        AuRestoMenuAuRestoDialogComponent,
        AuRestoMenuAuRestoPopupComponent,
        AuRestoMenuAuRestoDeleteDialogComponent,
        AuRestoMenuAuRestoDeletePopupComponent,
    ],
    providers: [
        AuRestoMenuAuRestoService,
        AuRestoMenuAuRestoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuRestoAuRestoMenuAuRestoModule {}
