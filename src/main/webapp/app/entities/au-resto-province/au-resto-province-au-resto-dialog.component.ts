import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuRestoProvinceAuResto } from './au-resto-province-au-resto.model';
import { AuRestoProvinceAuRestoPopupService } from './au-resto-province-au-resto-popup.service';
import { AuRestoProvinceAuRestoService } from './au-resto-province-au-resto.service';
import { AuRestoCountryAuResto, AuRestoCountryAuRestoService } from '../au-resto-country';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-au-resto-province-au-resto-dialog',
    templateUrl: './au-resto-province-au-resto-dialog.component.html'
})
export class AuRestoProvinceAuRestoDialogComponent implements OnInit {

    auRestoProvince: AuRestoProvinceAuResto;
    isSaving: boolean;

    aurestocountries: AuRestoCountryAuResto[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auRestoProvinceService: AuRestoProvinceAuRestoService,
        private auRestoCountryService: AuRestoCountryAuRestoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.auRestoCountryService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestocountries = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.auRestoProvince.id !== undefined) {
            this.subscribeToSaveResponse(
                this.auRestoProvinceService.update(this.auRestoProvince));
        } else {
            this.subscribeToSaveResponse(
                this.auRestoProvinceService.create(this.auRestoProvince));
        }
    }

    private subscribeToSaveResponse(result: Observable<AuRestoProvinceAuResto>) {
        result.subscribe((res: AuRestoProvinceAuResto) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AuRestoProvinceAuResto) {
        this.eventManager.broadcast({ name: 'auRestoProvinceListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAuRestoCountryById(index: number, item: AuRestoCountryAuResto) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-au-resto-province-au-resto-popup',
    template: ''
})
export class AuRestoProvinceAuRestoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoProvincePopupService: AuRestoProvinceAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.auRestoProvincePopupService
                    .open(AuRestoProvinceAuRestoDialogComponent as Component, params['id']);
            } else {
                this.auRestoProvincePopupService
                    .open(AuRestoProvinceAuRestoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
