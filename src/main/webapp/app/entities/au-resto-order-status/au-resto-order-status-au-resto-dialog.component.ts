import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuRestoOrderStatusAuResto } from './au-resto-order-status-au-resto.model';
import { AuRestoOrderStatusAuRestoPopupService } from './au-resto-order-status-au-resto-popup.service';
import { AuRestoOrderStatusAuRestoService } from './au-resto-order-status-au-resto.service';

@Component({
    selector: 'jhi-au-resto-order-status-au-resto-dialog',
    templateUrl: './au-resto-order-status-au-resto-dialog.component.html'
})
export class AuRestoOrderStatusAuRestoDialogComponent implements OnInit {

    auRestoOrderStatus: AuRestoOrderStatusAuResto;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auRestoOrderStatusService: AuRestoOrderStatusAuRestoService,
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
        if (this.auRestoOrderStatus.id !== undefined) {
            this.subscribeToSaveResponse(
                this.auRestoOrderStatusService.update(this.auRestoOrderStatus));
        } else {
            this.subscribeToSaveResponse(
                this.auRestoOrderStatusService.create(this.auRestoOrderStatus));
        }
    }

    private subscribeToSaveResponse(result: Observable<AuRestoOrderStatusAuResto>) {
        result.subscribe((res: AuRestoOrderStatusAuResto) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AuRestoOrderStatusAuResto) {
        this.eventManager.broadcast({ name: 'auRestoOrderStatusListModification', content: 'OK'});
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
    selector: 'jhi-au-resto-order-status-au-resto-popup',
    template: ''
})
export class AuRestoOrderStatusAuRestoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoOrderStatusPopupService: AuRestoOrderStatusAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.auRestoOrderStatusPopupService
                    .open(AuRestoOrderStatusAuRestoDialogComponent as Component, params['id']);
            } else {
                this.auRestoOrderStatusPopupService
                    .open(AuRestoOrderStatusAuRestoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
