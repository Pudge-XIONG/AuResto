import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuRestoSharedModule } from '../../shared';
import {
    AuRestoPhotoAuRestoService,
    AuRestoPhotoAuRestoPopupService,
    AuRestoPhotoAuRestoComponent,
    AuRestoPhotoAuRestoDetailComponent,
    AuRestoPhotoAuRestoDialogComponent,
    AuRestoPhotoAuRestoPopupComponent,
    AuRestoPhotoAuRestoDeletePopupComponent,
    AuRestoPhotoAuRestoDeleteDialogComponent,
    auRestoPhotoRoute,
    auRestoPhotoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...auRestoPhotoRoute,
    ...auRestoPhotoPopupRoute,
];

@NgModule({
    imports: [
        AuRestoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuRestoPhotoAuRestoComponent,
        AuRestoPhotoAuRestoDetailComponent,
        AuRestoPhotoAuRestoDialogComponent,
        AuRestoPhotoAuRestoDeleteDialogComponent,
        AuRestoPhotoAuRestoPopupComponent,
        AuRestoPhotoAuRestoDeletePopupComponent,
    ],
    entryComponents: [
        AuRestoPhotoAuRestoComponent,
        AuRestoPhotoAuRestoDialogComponent,
        AuRestoPhotoAuRestoPopupComponent,
        AuRestoPhotoAuRestoDeleteDialogComponent,
        AuRestoPhotoAuRestoDeletePopupComponent,
    ],
    providers: [
        AuRestoPhotoAuRestoService,
        AuRestoPhotoAuRestoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuRestoAuRestoPhotoAuRestoModule {}
