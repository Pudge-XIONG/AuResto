import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuRestoRestaurantTypeAuResto } from './au-resto-restaurant-type-au-resto.model';
import { AuRestoRestaurantTypeAuRestoPopupService } from './au-resto-restaurant-type-au-resto-popup.service';
import { AuRestoRestaurantTypeAuRestoService } from './au-resto-restaurant-type-au-resto.service';
import { AuRestoRestaurantAuResto, AuRestoRestaurantAuRestoService } from '../au-resto-restaurant';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-au-resto-restaurant-type-au-resto-dialog',
    templateUrl: './au-resto-restaurant-type-au-resto-dialog.component.html'
})
export class AuRestoRestaurantTypeAuRestoDialogComponent implements OnInit {

    auRestoRestaurantType: AuRestoRestaurantTypeAuResto;
    isSaving: boolean;

    aurestorestaurants: AuRestoRestaurantAuResto[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auRestoRestaurantTypeService: AuRestoRestaurantTypeAuRestoService,
        private auRestoRestaurantService: AuRestoRestaurantAuRestoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.auRestoRestaurantService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestorestaurants = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.auRestoRestaurantType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.auRestoRestaurantTypeService.update(this.auRestoRestaurantType));
        } else {
            this.subscribeToSaveResponse(
                this.auRestoRestaurantTypeService.create(this.auRestoRestaurantType));
        }
    }

    private subscribeToSaveResponse(result: Observable<AuRestoRestaurantTypeAuResto>) {
        result.subscribe((res: AuRestoRestaurantTypeAuResto) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AuRestoRestaurantTypeAuResto) {
        this.eventManager.broadcast({ name: 'auRestoRestaurantTypeListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-au-resto-restaurant-type-au-resto-popup',
    template: ''
})
export class AuRestoRestaurantTypeAuRestoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoRestaurantTypePopupService: AuRestoRestaurantTypeAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.auRestoRestaurantTypePopupService
                    .open(AuRestoRestaurantTypeAuRestoDialogComponent as Component, params['id']);
            } else {
                this.auRestoRestaurantTypePopupService
                    .open(AuRestoRestaurantTypeAuRestoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
