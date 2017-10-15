import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuRestoTableAuResto } from './au-resto-table-au-resto.model';
import { AuRestoTableAuRestoPopupService } from './au-resto-table-au-resto-popup.service';
import { AuRestoTableAuRestoService } from './au-resto-table-au-resto.service';
import { AuRestoRestaurantAuResto, AuRestoRestaurantAuRestoService } from '../au-resto-restaurant';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-au-resto-table-au-resto-dialog',
    templateUrl: './au-resto-table-au-resto-dialog.component.html'
})
export class AuRestoTableAuRestoDialogComponent implements OnInit {

    auRestoTable: AuRestoTableAuResto;
    isSaving: boolean;

    aurestorestaurants: AuRestoRestaurantAuResto[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auRestoTableService: AuRestoTableAuRestoService,
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
        if (this.auRestoTable.id !== undefined) {
            this.subscribeToSaveResponse(
                this.auRestoTableService.update(this.auRestoTable));
        } else {
            this.subscribeToSaveResponse(
                this.auRestoTableService.create(this.auRestoTable));
        }
    }

    private subscribeToSaveResponse(result: Observable<AuRestoTableAuResto>) {
        result.subscribe((res: AuRestoTableAuResto) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AuRestoTableAuResto) {
        this.eventManager.broadcast({ name: 'auRestoTableListModification', content: 'OK'});
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
    selector: 'jhi-au-resto-table-au-resto-popup',
    template: ''
})
export class AuRestoTableAuRestoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoTablePopupService: AuRestoTableAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.auRestoTablePopupService
                    .open(AuRestoTableAuRestoDialogComponent as Component, params['id']);
            } else {
                this.auRestoTablePopupService
                    .open(AuRestoTableAuRestoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
