import { BaseEntity } from './../../shared';

export class AuRestoProfileAuResto implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
    ) {
    }
}
