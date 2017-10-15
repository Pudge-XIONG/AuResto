import { BaseEntity } from './../../shared';

export class AuRestoGenderAuResto implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
    ) {
    }
}
