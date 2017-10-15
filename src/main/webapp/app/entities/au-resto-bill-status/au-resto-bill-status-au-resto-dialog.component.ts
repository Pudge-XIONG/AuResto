import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuRestoBillStatusAuResto } from './au-resto-bill-status-au-resto.model';
import { AuRestoBillStatusAuRestoPopupService } from './au-resto-bill-status-au-resto-popup.service';
import { AuRestoBillStatusAuRestoService } from './au-resto-bill-status-au-resto.service';

@Component({
    selector: 'jhi-au-resto-bill-status-au-resto-dialog',
    templateUrl: './au-resto-bill-status-au-resto-dialog.component.html'
})
export class AuRestoBillStatusAuRestoDialogComponent implements OnInit {

    auRestoBillStatus: AuRestoBillStatusAuResto;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auRestoBillStatusService: AuRestoBillStatusAuRestoService,
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
        if (this.auRestoBillStatus.id !== undefined) {
            this.subscribeToSaveResponse(
                this.auRestoBillStatusService.update(this.auRestoBillStatus));
        } else {
            this.subscribeToSaveResponse(
                this.auRestoBillStatusService.create(this.auRestoBillStatus));
        }
    }

    private subscribeToSaveResponse(result: Observable<AuRestoBillStatusAuResto>) {
        result.subscribe((res: AuRestoBillStatusAuResto) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AuRestoBillStatusAuResto) {
        this.eventManager.broadcast({ name: 'auRestoBillStatusListModification', content: 'OK'});
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
    selector: 'jhi-au-resto-bill-status-au-resto-popup',
    template: ''
})
export class AuRestoBillStatusAuRestoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoBillStatusPopupService: AuRestoBillStatusAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.auRestoBillStatusPopupService
                    .open(AuRestoBillStatusAuRestoDialogComponent as Component, params['id']);
            } else {
                this.auRestoBillStatusPopupService
                    .open(AuRestoBillStatusAuRestoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
