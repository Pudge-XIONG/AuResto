import { BaseEntity } from './../../shared';

export class AuRestoTableAuResto implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public maxPlaceNum?: number,
        public takenPlaceNum?: number,
        public window?: boolean,
        public outside?: boolean,
        public floor?: number,
        public available?: boolean,
        public auRestoRestaurant?: BaseEntity,
    ) {
        this.window = false;
        this.outside = false;
        this.available = false;
    }
}
