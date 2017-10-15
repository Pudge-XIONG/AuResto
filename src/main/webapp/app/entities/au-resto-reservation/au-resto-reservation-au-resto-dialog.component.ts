import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuRestoReservationAuResto } from './au-resto-reservation-au-resto.model';
import { AuRestoReservationAuRestoPopupService } from './au-resto-reservation-au-resto-popup.service';
import { AuRestoReservationAuRestoService } from './au-resto-reservation-au-resto.service';
import { AuRestoUserAuResto, AuRestoUserAuRestoService } from '../au-resto-user';
import { AuRestoOrderStatusAuResto, AuRestoOrderStatusAuRestoService } from '../au-resto-order-status';
import { AuRestoRestaurantAuResto, AuRestoRestaurantAuRestoService } from '../au-resto-restaurant';
import { AuRestoTableAuResto, AuRestoTableAuRestoService } from '../au-resto-table';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-au-resto-reservation-au-resto-dialog',
    templateUrl: './au-resto-reservation-au-resto-dialog.component.html'
})
export class AuRestoReservationAuRestoDialogComponent implements OnInit {

    auRestoReservation: AuRestoReservationAuResto;
    isSaving: boolean;

    aurestousers: AuRestoUserAuResto[];

    aurestoorderstatuses: AuRestoOrderStatusAuResto[];

    aurestorestaurants: AuRestoRestaurantAuResto[];

    aurestotables: AuRestoTableAuResto[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auRestoReservationService: AuRestoReservationAuRestoService,
        private auRestoUserService: AuRestoUserAuRestoService,
        private auRestoOrderStatusService: AuRestoOrderStatusAuRestoService,
        private auRestoRestaurantService: AuRestoRestaurantAuRestoService,
        private auRestoTableService: AuRestoTableAuRestoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.auRestoUserService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestousers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.auRestoOrderStatusService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestoorderstatuses = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.auRestoRestaurantService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestorestaurants = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.auRestoTableService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestotables = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.auRestoReservation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.auRestoReservationService.update(this.auRestoReservation));
        } else {
            this.subscribeToSaveResponse(
                this.auRestoReservationService.create(this.auRestoReservation));
        }
    }

    private subscribeToSaveResponse(result: Observable<AuRestoReservationAuResto>) {
        result.subscribe((res: AuRestoReservationAuResto) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AuRestoReservationAuResto) {
        this.eventManager.broadcast({ name: 'auRestoReservationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAuRestoUserById(index: number, item: AuRestoUserAuResto) {
        return item.id;
    }

    trackAuRestoOrderStatusById(index: number, item: AuRestoOrderStatusAuResto) {
        return item.id;
    }

    trackAuRestoRestaurantById(index: number, item: AuRestoRestaurantAuResto) {
        return item.id;
    }

    trackAuRestoTableById(index: number, item: AuRestoTableAuResto) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-au-resto-reservation-au-resto-popup',
    template: ''
})
export class AuRestoReservationAuRestoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoReservationPopupService: AuRestoReservationAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.auRestoReservationPopupService
                    .open(AuRestoReservationAuRestoDialogComponent as Component, params['id']);
            } else {
                this.auRestoReservationPopupService
                    .open(AuRestoReservationAuRestoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
