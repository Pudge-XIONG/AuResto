import { BaseEntity } from './../../shared';

export class AuRestoFormulaAuResto implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public price?: number,
        public auRestoMenu?: BaseEntity,
        public photos?: BaseEntity[],
        public recipes?: BaseEntity[],
        public type?: BaseEntity,
        public entree?: BaseEntity,
        public dish?: BaseEntity,
        public dessert?: BaseEntity,
        public auRestoOrder?: BaseEntity,
    ) {
    }
}
