import { BaseEntity } from './../../shared';

export class AuRestoReservationAuResto implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public reserveDate?: any,
        public reserveForDate?: any,
        public commander?: BaseEntity,
        public status?: BaseEntity,
        public restaurant?: BaseEntity,
        public table?: BaseEntity,
        public auRestoUser?: BaseEntity,
    ) {
    }
}
