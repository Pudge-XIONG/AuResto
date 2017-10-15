import { BaseEntity } from './../../shared';

export class AuRestoPhotoAuResto implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public imageContentType?: string,
        public image?: any,
        public auRestoRestaurant?: BaseEntity,
        public auRestoMenu?: BaseEntity,
        public auRestoFormula?: BaseEntity,
        public auRestoRecipe?: BaseEntity,
    ) {
    }
}
