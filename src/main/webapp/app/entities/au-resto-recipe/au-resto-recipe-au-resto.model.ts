import { BaseEntity } from './../../shared';

export class AuRestoRecipeAuResto implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public comment?: string,
        public price?: number,
        public auRestoFormula?: BaseEntity,
        public photos?: BaseEntity[],
        public type?: BaseEntity,
        public auRestoOrder?: BaseEntity,
    ) {
    }
}
