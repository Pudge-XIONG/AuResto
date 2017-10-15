import { BaseEntity } from './../../shared';

export class AuRestoOrderStatusAuResto implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
    ) {
    }
}
