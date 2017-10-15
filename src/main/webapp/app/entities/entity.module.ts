import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AuRestoAuRestoLocationAuRestoModule } from './au-resto-location/au-resto-location-au-resto.module';
import { AuRestoAuRestoCityAuRestoModule } from './au-resto-city/au-resto-city-au-resto.module';
import { AuRestoAuRestoProvinceAuRestoModule } from './au-resto-province/au-resto-province-au-resto.module';
import { AuRestoAuRestoCountryAuRestoModule } from './au-resto-country/au-resto-country-au-resto.module';
import { AuRestoAuRestoRestaurantAuRestoModule } from './au-resto-restaurant/au-resto-restaurant-au-resto.module';
import { AuRestoAuRestoRestaurantTypeAuRestoModule } from './au-resto-restaurant-type/au-resto-restaurant-type-au-resto.module';
import { AuRestoAuRestoMenuAuRestoModule } from './au-resto-menu/au-resto-menu-au-resto.module';
import { AuRestoAuRestoFormulaAuRestoModule } from './au-resto-formula/au-resto-formula-au-resto.module';
import { AuRestoAuRestoFormulaTypeAuRestoModule } from './au-resto-formula-type/au-resto-formula-type-au-resto.module';
import { AuRestoAuRestoRecipeAuRestoModule } from './au-resto-recipe/au-resto-recipe-au-resto.module';
import { AuRestoAuRestoRecipeTypeAuRestoModule } from './au-resto-recipe-type/au-resto-recipe-type-au-resto.module';
import { AuRestoAuRestoReservationAuRestoModule } from './au-resto-reservation/au-resto-reservation-au-resto.module';
import { AuRestoAuRestoOrderAuRestoModule } from './au-resto-order/au-resto-order-au-resto.module';
import { AuRestoAuRestoOrderTypeAuRestoModule } from './au-resto-order-type/au-resto-order-type-au-resto.module';
import { AuRestoAuRestoOrderStatusAuRestoModule } from './au-resto-order-status/au-resto-order-status-au-resto.module';
import { AuRestoAuRestoTableAuRestoModule } from './au-resto-table/au-resto-table-au-resto.module';
import { AuRestoAuRestoPhotoAuRestoModule } from './au-resto-photo/au-resto-photo-au-resto.module';
import { AuRestoAuRestoUserAuRestoModule } from './au-resto-user/au-resto-user-au-resto.module';
import { AuRestoAuRestoGenderAuRestoModule } from './au-resto-gender/au-resto-gender-au-resto.module';
import { AuRestoAuRestoProfileAuRestoModule } from './au-resto-profile/au-resto-profile-au-resto.module';
import { AuRestoAuRestoBillAuRestoModule } from './au-resto-bill/au-resto-bill-au-resto.module';
import { AuRestoAuRestoBillStatusAuRestoModule } from './au-resto-bill-status/au-resto-bill-status-au-resto.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        AuRestoAuRestoLocationAuRestoModule,
        AuRestoAuRestoCityAuRestoModule,
        AuRestoAuRestoProvinceAuRestoModule,
        AuRestoAuRestoCountryAuRestoModule,
        AuRestoAuRestoRestaurantAuRestoModule,
        AuRestoAuRestoRestaurantTypeAuRestoModule,
        AuRestoAuRestoMenuAuRestoModule,
        AuRestoAuRestoFormulaAuRestoModule,
        AuRestoAuRestoFormulaTypeAuRestoModule,
        AuRestoAuRestoRecipeAuRestoModule,
        AuRestoAuRestoRecipeTypeAuRestoModule,
        AuRestoAuRestoReservationAuRestoModule,
        AuRestoAuRestoOrderAuRestoModule,
        AuRestoAuRestoOrderTypeAuRestoModule,
        AuRestoAuRestoOrderStatusAuRestoModule,
        AuRestoAuRestoTableAuRestoModule,
        AuRestoAuRestoPhotoAuRestoModule,
        AuRestoAuRestoUserAuRestoModule,
        AuRestoAuRestoGenderAuRestoModule,
        AuRestoAuRestoProfileAuRestoModule,
        AuRestoAuRestoBillAuRestoModule,
        AuRestoAuRestoBillStatusAuRestoModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuRestoEntityModule {}
