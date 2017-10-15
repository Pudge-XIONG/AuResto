import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuRestoFormulaAuResto } from './au-resto-formula-au-resto.model';
import { AuRestoFormulaAuRestoPopupService } from './au-resto-formula-au-resto-popup.service';
import { AuRestoFormulaAuRestoService } from './au-resto-formula-au-resto.service';
import { AuRestoMenuAuResto, AuRestoMenuAuRestoService } from '../au-resto-menu';
import { AuRestoRecipeAuResto, AuRestoRecipeAuRestoService } from '../au-resto-recipe';
import { AuRestoFormulaTypeAuResto, AuRestoFormulaTypeAuRestoService } from '../au-resto-formula-type';
import { AuRestoOrderAuResto, AuRestoOrderAuRestoService } from '../au-resto-order';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-au-resto-formula-au-resto-dialog',
    templateUrl: './au-resto-formula-au-resto-dialog.component.html'
})
export class AuRestoFormulaAuRestoDialogComponent implements OnInit {

    auRestoFormula: AuRestoFormulaAuResto;
    isSaving: boolean;

    aurestomenus: AuRestoMenuAuResto[];

    aurestoformulatypes: AuRestoFormulaTypeAuResto[];

    aurestorecipes: AuRestoRecipeAuResto[];

    aurestoorders: AuRestoOrderAuResto[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auRestoFormulaService: AuRestoFormulaAuRestoService,
        private auRestoMenuService: AuRestoMenuAuRestoService,
        private auRestoRecipeService: AuRestoRecipeAuRestoService,
        private auRestoFormulaTypeService: AuRestoFormulaTypeAuRestoService,
        private auRestoOrderService: AuRestoOrderAuRestoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.auRestoMenuService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestomenus = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.auRestoFormulaTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestoformulatypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.auRestoRecipeService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestorecipes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.auRestoOrderService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestoorders = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.auRestoFormula.id !== undefined) {
            this.subscribeToSaveResponse(
                this.auRestoFormulaService.update(this.auRestoFormula));
        } else {
            this.subscribeToSaveResponse(
                this.auRestoFormulaService.create(this.auRestoFormula));
        }
    }

    private subscribeToSaveResponse(result: Observable<AuRestoFormulaAuResto>) {
        result.subscribe((res: AuRestoFormulaAuResto) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AuRestoFormulaAuResto) {
        this.eventManager.broadcast({ name: 'auRestoFormulaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAuRestoMenuById(index: number, item: AuRestoMenuAuResto) {
        return item.id;
    }

    trackAuRestoFormulaTypeById(index: number, item: AuRestoFormulaTypeAuResto) {
        return item.id;
    }

    trackAuRestoRecipeById(index: number, item: AuRestoRecipeAuResto) {
        return item.id;
    }

    trackAuRestoOrderById(index: number, item: AuRestoOrderAuResto) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-au-resto-formula-au-resto-popup',
    template: ''
})
export class AuRestoFormulaAuRestoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoFormulaPopupService: AuRestoFormulaAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.auRestoFormulaPopupService
                    .open(AuRestoFormulaAuRestoDialogComponent as Component, params['id']);
            } else {
                this.auRestoFormulaPopupService
                    .open(AuRestoFormulaAuRestoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
