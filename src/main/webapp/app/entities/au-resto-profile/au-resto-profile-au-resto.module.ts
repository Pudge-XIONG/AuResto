import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuRestoSharedModule } from '../../shared';
import {
    AuRestoProfileAuRestoService,
    AuRestoProfileAuRestoPopupService,
    AuRestoProfileAuRestoComponent,
    AuRestoProfileAuRestoDetailComponent,
    AuRestoProfileAuRestoDialogComponent,
    AuRestoProfileAuRestoPopupComponent,
    AuRestoProfileAuRestoDeletePopupComponent,
    AuRestoProfileAuRestoDeleteDialogComponent,
    auRestoProfileRoute,
    auRestoProfilePopupRoute,
} from './';

const ENTITY_STATES = [
    ...auRestoProfileRoute,
    ...auRestoProfilePopupRoute,
];

@NgModule({
    imports: [
        AuRestoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuRestoProfileAuRestoComponent,
        AuRestoProfileAuRestoDetailComponent,
        AuRestoProfileAuRestoDialogComponent,
        AuRestoProfileAuRestoDeleteDialogComponent,
        AuRestoProfileAuRestoPopupComponent,
        AuRestoProfileAuRestoDeletePopupComponent,
    ],
    entryComponents: [
        AuRestoProfileAuRestoComponent,
        AuRestoProfileAuRestoDialogComponent,
        AuRestoProfileAuRestoPopupComponent,
        AuRestoProfileAuRestoDeleteDialogComponent,
        AuRestoProfileAuRestoDeletePopupComponent,
    ],
    providers: [
        AuRestoProfileAuRestoService,
        AuRestoProfileAuRestoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuRestoAuRestoProfileAuRestoModule {}
