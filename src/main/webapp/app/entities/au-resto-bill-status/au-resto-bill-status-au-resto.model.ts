import { BaseEntity } from './../../shared';

export class AuRestoBillStatusAuResto implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
    ) {
    }
}
