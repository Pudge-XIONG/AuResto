import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuRestoCountryAuResto } from './au-resto-country-au-resto.model';
import { AuRestoCountryAuRestoPopupService } from './au-resto-country-au-resto-popup.service';
import { AuRestoCountryAuRestoService } from './au-resto-country-au-resto.service';

@Component({
    selector: 'jhi-au-resto-country-au-resto-dialog',
    templateUrl: './au-resto-country-au-resto-dialog.component.html'
})
export class AuRestoCountryAuRestoDialogComponent implements OnInit {

    auRestoCountry: AuRestoCountryAuResto;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auRestoCountryService: AuRestoCountryAuRestoService,
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
        if (this.auRestoCountry.id !== undefined) {
            this.subscribeToSaveResponse(
                this.auRestoCountryService.update(this.auRestoCountry));
        } else {
            this.subscribeToSaveResponse(
                this.auRestoCountryService.create(this.auRestoCountry));
        }
    }

    private subscribeToSaveResponse(result: Observable<AuRestoCountryAuResto>) {
        result.subscribe((res: AuRestoCountryAuResto) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AuRestoCountryAuResto) {
        this.eventManager.broadcast({ name: 'auRestoCountryListModification', content: 'OK'});
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
    selector: 'jhi-au-resto-country-au-resto-popup',
    template: ''
})
export class AuRestoCountryAuRestoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoCountryPopupService: AuRestoCountryAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.auRestoCountryPopupService
                    .open(AuRestoCountryAuRestoDialogComponent as Component, params['id']);
            } else {
                this.auRestoCountryPopupService
                    .open(AuRestoCountryAuRestoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
