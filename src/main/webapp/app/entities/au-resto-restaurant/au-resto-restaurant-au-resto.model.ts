import { BaseEntity } from './../../shared';

export class AuRestoRestaurantAuResto implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public description?: any,
        public openTime?: any,
        public closeTime?: any,
        public location?: BaseEntity,
        public types?: BaseEntity[],
        public photos?: BaseEntity[],
        public menus?: BaseEntity[],
        public tables?: BaseEntity[],
        public owner?: BaseEntity,
    ) {
    }
}
