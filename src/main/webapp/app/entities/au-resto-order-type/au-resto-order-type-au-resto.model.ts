import { BaseEntity } from './../../shared';

export class AuRestoOrderTypeAuResto implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
    ) {
    }
}
