import { BaseEntity } from './../../shared';

export class AuRestoProvinceAuResto implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public code?: string,
        public country?: BaseEntity,
    ) {
    }
}
