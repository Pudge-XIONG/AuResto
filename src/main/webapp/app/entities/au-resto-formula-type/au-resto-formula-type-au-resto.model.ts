import { BaseEntity } from './../../shared';

export class AuRestoFormulaTypeAuResto implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
    ) {
    }
}
