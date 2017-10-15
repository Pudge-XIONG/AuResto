import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuRestoLocationAuResto } from './au-resto-location-au-resto.model';
import { AuRestoLocationAuRestoPopupService } from './au-resto-location-au-resto-popup.service';
import { AuRestoLocationAuRestoService } from './au-resto-location-au-resto.service';
import { AuRestoCityAuResto, AuRestoCityAuRestoService } from '../au-resto-city';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-au-resto-location-au-resto-dialog',
    templateUrl: './au-resto-location-au-resto-dialog.component.html'
})
export class AuRestoLocationAuRestoDialogComponent implements OnInit {

    auRestoLocation: AuRestoLocationAuResto;
    isSaving: boolean;

    aurestocities: AuRestoCityAuResto[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auRestoLocationService: AuRestoLocationAuRestoService,
        private auRestoCityService: AuRestoCityAuRestoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.auRestoCityService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestocities = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.auRestoLocation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.auRestoLocationService.update(this.auRestoLocation));
        } else {
            this.subscribeToSaveResponse(
                this.auRestoLocationService.create(this.auRestoLocation));
        }
    }

    private subscribeToSaveResponse(result: Observable<AuRestoLocationAuResto>) {
        result.subscribe((res: AuRestoLocationAuResto) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AuRestoLocationAuResto) {
        this.eventManager.broadcast({ name: 'auRestoLocationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAuRestoCityById(index: number, item: AuRestoCityAuResto) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-au-resto-location-au-resto-popup',
    template: ''
})
export class AuRestoLocationAuRestoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoLocationPopupService: AuRestoLocationAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.auRestoLocationPopupService
                    .open(AuRestoLocationAuRestoDialogComponent as Component, params['id']);
            } else {
                this.auRestoLocationPopupService
                    .open(AuRestoLocationAuRestoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
