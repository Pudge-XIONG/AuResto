import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuRestoGenderAuResto } from './au-resto-gender-au-resto.model';
import { AuRestoGenderAuRestoPopupService } from './au-resto-gender-au-resto-popup.service';
import { AuRestoGenderAuRestoService } from './au-resto-gender-au-resto.service';

@Component({
    selector: 'jhi-au-resto-gender-au-resto-dialog',
    templateUrl: './au-resto-gender-au-resto-dialog.component.html'
})
export class AuRestoGenderAuRestoDialogComponent implements OnInit {

    auRestoGender: AuRestoGenderAuResto;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auRestoGenderService: AuRestoGenderAuRestoService,
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
        if (this.auRestoGender.id !== undefined) {
            this.subscribeToSaveResponse(
                this.auRestoGenderService.update(this.auRestoGender));
        } else {
            this.subscribeToSaveResponse(
                this.auRestoGenderService.create(this.auRestoGender));
        }
    }

    private subscribeToSaveResponse(result: Observable<AuRestoGenderAuResto>) {
        result.subscribe((res: AuRestoGenderAuResto) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AuRestoGenderAuResto) {
        this.eventManager.broadcast({ name: 'auRestoGenderListModification', content: 'OK'});
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
    selector: 'jhi-au-resto-gender-au-resto-popup',
    template: ''
})
export class AuRestoGenderAuRestoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoGenderPopupService: AuRestoGenderAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.auRestoGenderPopupService
                    .open(AuRestoGenderAuRestoDialogComponent as Component, params['id']);
            } else {
                this.auRestoGenderPopupService
                    .open(AuRestoGenderAuRestoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
