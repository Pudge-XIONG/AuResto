import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuRestoBillAuResto } from './au-resto-bill-au-resto.model';
import { AuRestoBillAuRestoPopupService } from './au-resto-bill-au-resto-popup.service';
import { AuRestoBillAuRestoService } from './au-resto-bill-au-resto.service';
import { AuRestoBillStatusAuResto, AuRestoBillStatusAuRestoService } from '../au-resto-bill-status';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-au-resto-bill-au-resto-dialog',
    templateUrl: './au-resto-bill-au-resto-dialog.component.html'
})
export class AuRestoBillAuRestoDialogComponent implements OnInit {

    auRestoBill: AuRestoBillAuResto;
    isSaving: boolean;

    aurestobillstatuses: AuRestoBillStatusAuResto[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auRestoBillService: AuRestoBillAuRestoService,
        private auRestoBillStatusService: AuRestoBillStatusAuRestoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.auRestoBillStatusService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestobillstatuses = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.auRestoBill.id !== undefined) {
            this.subscribeToSaveResponse(
                this.auRestoBillService.update(this.auRestoBill));
        } else {
            this.subscribeToSaveResponse(
                this.auRestoBillService.create(this.auRestoBill));
        }
    }

    private subscribeToSaveResponse(result: Observable<AuRestoBillAuResto>) {
        result.subscribe((res: AuRestoBillAuResto) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AuRestoBillAuResto) {
        this.eventManager.broadcast({ name: 'auRestoBillListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAuRestoBillStatusById(index: number, item: AuRestoBillStatusAuResto) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-au-resto-bill-au-resto-popup',
    template: ''
})
export class AuRestoBillAuRestoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoBillPopupService: AuRestoBillAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.auRestoBillPopupService
                    .open(AuRestoBillAuRestoDialogComponent as Component, params['id']);
            } else {
                this.auRestoBillPopupService
                    .open(AuRestoBillAuRestoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
