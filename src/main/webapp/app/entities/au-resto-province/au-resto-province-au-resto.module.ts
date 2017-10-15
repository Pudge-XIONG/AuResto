import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuRestoSharedModule } from '../../shared';
import {
    AuRestoProvinceAuRestoService,
    AuRestoProvinceAuRestoPopupService,
    AuRestoProvinceAuRestoComponent,
    AuRestoProvinceAuRestoDetailComponent,
    AuRestoProvinceAuRestoDialogComponent,
    AuRestoProvinceAuRestoPopupComponent,
    AuRestoProvinceAuRestoDeletePopupComponent,
    AuRestoProvinceAuRestoDeleteDialogComponent,
    auRestoProvinceRoute,
    auRestoProvincePopupRoute,
} from './';

const ENTITY_STATES = [
    ...auRestoProvinceRoute,
    ...auRestoProvincePopupRoute,
];

@NgModule({
    imports: [
        AuRestoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuRestoProvinceAuRestoComponent,
        AuRestoProvinceAuRestoDetailComponent,
        AuRestoProvinceAuRestoDialogComponent,
        AuRestoProvinceAuRestoDeleteDialogComponent,
        AuRestoProvinceAuRestoPopupComponent,
        AuRestoProvinceAuRestoDeletePopupComponent,
    ],
    entryComponents: [
        AuRestoProvinceAuRestoComponent,
        AuRestoProvinceAuRestoDialogComponent,
        AuRestoProvinceAuRestoPopupComponent,
        AuRestoProvinceAuRestoDeleteDialogComponent,
        AuRestoProvinceAuRestoDeletePopupComponent,
    ],
    providers: [
        AuRestoProvinceAuRestoService,
        AuRestoProvinceAuRestoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuRestoAuRestoProvinceAuRestoModule {}
