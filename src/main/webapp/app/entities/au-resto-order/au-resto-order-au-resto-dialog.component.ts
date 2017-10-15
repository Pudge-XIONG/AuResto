import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuRestoOrderAuResto } from './au-resto-order-au-resto.model';
import { AuRestoOrderAuRestoPopupService } from './au-resto-order-au-resto-popup.service';
import { AuRestoOrderAuRestoService } from './au-resto-order-au-resto.service';
import { AuRestoRestaurantAuResto, AuRestoRestaurantAuRestoService } from '../au-resto-restaurant';
import { AuRestoTableAuResto, AuRestoTableAuRestoService } from '../au-resto-table';
import { AuRestoUserAuResto, AuRestoUserAuRestoService } from '../au-resto-user';
import { AuRestoOrderTypeAuResto, AuRestoOrderTypeAuRestoService } from '../au-resto-order-type';
import { AuRestoOrderStatusAuResto, AuRestoOrderStatusAuRestoService } from '../au-resto-order-status';
import { AuRestoBillAuResto, AuRestoBillAuRestoService } from '../au-resto-bill';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-au-resto-order-au-resto-dialog',
    templateUrl: './au-resto-order-au-resto-dialog.component.html'
})
export class AuRestoOrderAuRestoDialogComponent implements OnInit {

    auRestoOrder: AuRestoOrderAuResto;
    isSaving: boolean;

    aurestorestaurants: AuRestoRestaurantAuResto[];

    aurestotables: AuRestoTableAuResto[];

    aurestousers: AuRestoUserAuResto[];

    aurestoordertypes: AuRestoOrderTypeAuResto[];

    aurestoorderstatuses: AuRestoOrderStatusAuResto[];

    aurestobills: AuRestoBillAuResto[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auRestoOrderService: AuRestoOrderAuRestoService,
        private auRestoRestaurantService: AuRestoRestaurantAuRestoService,
        private auRestoTableService: AuRestoTableAuRestoService,
        private auRestoUserService: AuRestoUserAuRestoService,
        private auRestoOrderTypeService: AuRestoOrderTypeAuRestoService,
        private auRestoOrderStatusService: AuRestoOrderStatusAuRestoService,
        private auRestoBillService: AuRestoBillAuRestoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.auRestoRestaurantService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestorestaurants = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.auRestoTableService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestotables = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.auRestoUserService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestousers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.auRestoOrderTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestoordertypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.auRestoOrderStatusService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestoorderstatuses = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.auRestoBillService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestobills = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.auRestoOrder.id !== undefined) {
            this.subscribeToSaveResponse(
                this.auRestoOrderService.update(this.auRestoOrder));
        } else {
            this.subscribeToSaveResponse(
                this.auRestoOrderService.create(this.auRestoOrder));
        }
    }

    private subscribeToSaveResponse(result: Observable<AuRestoOrderAuResto>) {
        result.subscribe((res: AuRestoOrderAuResto) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AuRestoOrderAuResto) {
        this.eventManager.broadcast({ name: 'auRestoOrderListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAuRestoRestaurantById(index: number, item: AuRestoRestaurantAuResto) {
        return item.id;
    }

    trackAuRestoTableById(index: number, item: AuRestoTableAuResto) {
        return item.id;
    }

    trackAuRestoUserById(index: number, item: AuRestoUserAuResto) {
        return item.id;
    }

    trackAuRestoOrderTypeById(index: number, item: AuRestoOrderTypeAuResto) {
        return item.id;
    }

    trackAuRestoOrderStatusById(index: number, item: AuRestoOrderStatusAuResto) {
        return item.id;
    }

    trackAuRestoBillById(index: number, item: AuRestoBillAuResto) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-au-resto-order-au-resto-popup',
    template: ''
})
export class AuRestoOrderAuRestoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoOrderPopupService: AuRestoOrderAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.auRestoOrderPopupService
                    .open(AuRestoOrderAuRestoDialogComponent as Component, params['id']);
            } else {
                this.auRestoOrderPopupService
                    .open(AuRestoOrderAuRestoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
