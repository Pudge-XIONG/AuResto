import { BaseEntity } from './../../shared';

export class AuRestoLocationAuResto implements BaseEntity {
    constructor(
        public id?: number,
        public address?: string,
        public latitude?: number,
        public longitude?: number,
        public city?: BaseEntity,
    ) {
    }
}
