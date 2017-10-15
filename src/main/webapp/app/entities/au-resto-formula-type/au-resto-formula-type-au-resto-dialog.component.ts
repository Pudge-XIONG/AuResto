import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuRestoFormulaTypeAuResto } from './au-resto-formula-type-au-resto.model';
import { AuRestoFormulaTypeAuRestoPopupService } from './au-resto-formula-type-au-resto-popup.service';
import { AuRestoFormulaTypeAuRestoService } from './au-resto-formula-type-au-resto.service';

@Component({
    selector: 'jhi-au-resto-formula-type-au-resto-dialog',
    templateUrl: './au-resto-formula-type-au-resto-dialog.component.html'
})
export class AuRestoFormulaTypeAuRestoDialogComponent implements OnInit {

    auRestoFormulaType: AuRestoFormulaTypeAuResto;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auRestoFormulaTypeService: AuRestoFormulaTypeAuRestoService,
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
        if (this.auRestoFormulaType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.auRestoFormulaTypeService.update(this.auRestoFormulaType));
        } else {
            this.subscribeToSaveResponse(
                this.auRestoFormulaTypeService.create(this.auRestoFormulaType));
        }
    }

    private subscribeToSaveResponse(result: Observable<AuRestoFormulaTypeAuResto>) {
        result.subscribe((res: AuRestoFormulaTypeAuResto) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AuRestoFormulaTypeAuResto) {
        this.eventManager.broadcast({ name: 'auRestoFormulaTypeListModification', content: 'OK'});
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
    selector: 'jhi-au-resto-formula-type-au-resto-popup',
    template: ''
})
export class AuRestoFormulaTypeAuRestoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoFormulaTypePopupService: AuRestoFormulaTypeAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.auRestoFormulaTypePopupService
                    .open(AuRestoFormulaTypeAuRestoDialogComponent as Component, params['id']);
            } else {
                this.auRestoFormulaTypePopupService
                    .open(AuRestoFormulaTypeAuRestoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
