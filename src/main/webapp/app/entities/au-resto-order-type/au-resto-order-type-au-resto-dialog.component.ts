import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuRestoOrderTypeAuResto } from './au-resto-order-type-au-resto.model';
import { AuRestoOrderTypeAuRestoPopupService } from './au-resto-order-type-au-resto-popup.service';
import { AuRestoOrderTypeAuRestoService } from './au-resto-order-type-au-resto.service';

@Component({
    selector: 'jhi-au-resto-order-type-au-resto-dialog',
    templateUrl: './au-resto-order-type-au-resto-dialog.component.html'
})
export class AuRestoOrderTypeAuRestoDialogComponent implements OnInit {

    auRestoOrderType: AuRestoOrderTypeAuResto;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auRestoOrderTypeService: AuRestoOrderTypeAuRestoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.auRestoOrderType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.auRestoOrderTypeService.update(this.auRestoOrderType));
        } else {
            this.subscribeToSaveResponse(
                this.auRestoOrderTypeService.create(this.auRestoOrderType));
        }
    }

    private subscribeToSaveResponse(result: Observable<AuRestoOrderTypeAuResto>) {
        result.subscribe((res: AuRestoOrderTypeAuResto) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AuRestoOrderTypeAuResto) {
        this.eventManager.broadcast({ name: 'auRestoOrderTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-au-resto-order-type-au-resto-popup',
    template: ''
})
export class AuRestoOrderTypeAuRestoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoOrderTypePopupService: AuRestoOrderTypeAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.auRestoOrderTypePopupService
                    .open(AuRestoOrderTypeAuRestoDialogComponent as Component, params['id']);
            } else {
                this.auRestoOrderTypePopupService
                    .open(AuRestoOrderTypeAuRestoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
