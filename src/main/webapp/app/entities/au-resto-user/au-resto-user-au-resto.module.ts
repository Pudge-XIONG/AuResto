import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuRestoSharedModule } from '../../shared';
import {
    AuRestoUserAuRestoService,
    AuRestoUserAuRestoPopupService,
    AuRestoUserAuRestoComponent,
    AuRestoUserAuRestoDetailComponent,
    AuRestoUserAuRestoDialogComponent,
    AuRestoUserAuRestoPopupComponent,
    AuRestoUserAuRestoDeletePopupComponent,
    AuRestoUserAuRestoDeleteDialogComponent,
    auRestoUserRoute,
    auRestoUserPopupRoute,
    AuRestoUserAuRestoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...auRestoUserRoute,
    ...auRestoUserPopupRoute,
];

@NgModule({
    imports: [
        AuRestoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuRestoUserAuRestoComponent,
        AuRestoUserAuRestoDetailComponent,
        AuRestoUserAuRestoDialogComponent,
        AuRestoUserAuRestoDeleteDialogComponent,
        AuRestoUserAuRestoPopupComponent,
        AuRestoUserAuRestoDeletePopupComponent,
    ],
    entryComponents: [
        AuRestoUserAuRestoComponent,
        AuRestoUserAuRestoDialogComponent,
        AuRestoUserAuRestoPopupComponent,
        AuRestoUserAuRestoDeleteDialogComponent,
        AuRestoUserAuRestoDeletePopupComponent,
    ],
    providers: [
        AuRestoUserAuRestoService,
        AuRestoUserAuRestoPopupService,
        AuRestoUserAuRestoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuRestoAuRestoUserAuRestoModule {}
