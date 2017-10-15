import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuRestoRecipeTypeAuResto } from './au-resto-recipe-type-au-resto.model';
import { AuRestoRecipeTypeAuRestoPopupService } from './au-resto-recipe-type-au-resto-popup.service';
import { AuRestoRecipeTypeAuRestoService } from './au-resto-recipe-type-au-resto.service';

@Component({
    selector: 'jhi-au-resto-recipe-type-au-resto-dialog',
    templateUrl: './au-resto-recipe-type-au-resto-dialog.component.html'
})
export class AuRestoRecipeTypeAuRestoDialogComponent implements OnInit {

    auRestoRecipeType: AuRestoRecipeTypeAuResto;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auRestoRecipeTypeService: AuRestoRecipeTypeAuRestoService,
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
        if (this.auRestoRecipeType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.auRestoRecipeTypeService.update(this.auRestoRecipeType));
        } else {
            this.subscribeToSaveResponse(
                this.auRestoRecipeTypeService.create(this.auRestoRecipeType));
        }
    }

    private subscribeToSaveResponse(result: Observable<AuRestoRecipeTypeAuResto>) {
        result.subscribe((res: AuRestoRecipeTypeAuResto) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AuRestoRecipeTypeAuResto) {
        this.eventManager.broadcast({ name: 'auRestoRecipeTypeListModification', content: 'OK'});
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
    selector: 'jhi-au-resto-recipe-type-au-resto-popup',
    template: ''
})
export class AuRestoRecipeTypeAuRestoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoRecipeTypePopupService: AuRestoRecipeTypeAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.auRestoRecipeTypePopupService
                    .open(AuRestoRecipeTypeAuRestoDialogComponent as Component, params['id']);
            } else {
                this.auRestoRecipeTypePopupService
                    .open(AuRestoRecipeTypeAuRestoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
