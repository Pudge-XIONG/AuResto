import { BaseEntity } from './../../shared';

export class AuRestoCityAuResto implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public postCode?: string,
        public province?: BaseEntity,
    ) {
    }
}
