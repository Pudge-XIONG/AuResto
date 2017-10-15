import { BaseEntity } from './../../shared';

export class AuRestoOrderAuResto implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public date?: any,
        public formulas?: BaseEntity[],
        public recipes?: BaseEntity[],
        public restaurant?: BaseEntity,
        public table?: BaseEntity,
        public commander?: BaseEntity,
        public type?: BaseEntity,
        public status?: BaseEntity,
        public auRestoUser?: BaseEntity,
        public auRestoBill?: BaseEntity,
    ) {
    }
}
