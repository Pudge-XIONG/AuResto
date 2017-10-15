import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuRestoCityAuResto } from './au-resto-city-au-resto.model';
import { AuRestoCityAuRestoPopupService } from './au-resto-city-au-resto-popup.service';
import { AuRestoCityAuRestoService } from './au-resto-city-au-resto.service';
import { AuRestoProvinceAuResto, AuRestoProvinceAuRestoService } from '../au-resto-province';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-au-resto-city-au-resto-dialog',
    templateUrl: './au-resto-city-au-resto-dialog.component.html'
})
export class AuRestoCityAuRestoDialogComponent implements OnInit {

    auRestoCity: AuRestoCityAuResto;
    isSaving: boolean;

    aurestoprovinces: AuRestoProvinceAuResto[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auRestoCityService: AuRestoCityAuRestoService,
        private auRestoProvinceService: AuRestoProvinceAuRestoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.auRestoProvinceService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestoprovinces = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.auRestoCity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.auRestoCityService.update(this.auRestoCity));
        } else {
            this.subscribeToSaveResponse(
                this.auRestoCityService.create(this.auRestoCity));
        }
    }

    private subscribeToSaveResponse(result: Observable<AuRestoCityAuResto>) {
        result.subscribe((res: AuRestoCityAuResto) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AuRestoCityAuResto) {
        this.eventManager.broadcast({ name: 'auRestoCityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAuRestoProvinceById(index: number, item: AuRestoProvinceAuResto) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-au-resto-city-au-resto-popup',
    template: ''
})
export class AuRestoCityAuRestoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoCityPopupService: AuRestoCityAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.auRestoCityPopupService
                    .open(AuRestoCityAuRestoDialogComponent as Component, params['id']);
            } else {
                this.auRestoCityPopupService
                    .open(AuRestoCityAuRestoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
