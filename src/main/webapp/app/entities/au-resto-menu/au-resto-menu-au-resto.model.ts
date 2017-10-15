import { BaseEntity } from './../../shared';

export class AuRestoMenuAuResto implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public date?: any,
        public auRestoRestaurant?: BaseEntity,
        public photos?: BaseEntity[],
        public formulas?: BaseEntity[],
    ) {
    }
}
