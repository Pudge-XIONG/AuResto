import { BaseEntity } from './../../shared';

export class AuRestoBillAuResto implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public date?: any,
        public orders?: BaseEntity[],
        public status?: BaseEntity,
    ) {
    }
}
