import { BaseEntity } from './../../shared';

export class AuRestoRestaurantTypeAuResto implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public auRestoRestaurant?: BaseEntity,
    ) {
    }
}
