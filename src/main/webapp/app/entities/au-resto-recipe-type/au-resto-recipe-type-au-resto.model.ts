import { BaseEntity } from './../../shared';

export class AuRestoRecipeTypeAuResto implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
    ) {
    }
}
