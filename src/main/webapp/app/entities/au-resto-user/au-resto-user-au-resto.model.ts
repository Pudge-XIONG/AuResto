import { BaseEntity } from './../../shared';

export class AuRestoUserAuResto implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public middleName?: string,
        public lastName?: string,
        public login?: string,
        public password?: string,
        public photo?: BaseEntity,
        public orders?: BaseEntity[],
        public reservations?: BaseEntity[],
        public gender?: BaseEntity,
        public profile?: BaseEntity,
    ) {
    }
}
