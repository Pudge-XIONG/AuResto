import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuRestoRecipeAuResto } from './au-resto-recipe-au-resto.model';
import { AuRestoRecipeAuRestoPopupService } from './au-resto-recipe-au-resto-popup.service';
import { AuRestoRecipeAuRestoService } from './au-resto-recipe-au-resto.service';
import { AuRestoFormulaAuResto, AuRestoFormulaAuRestoService } from '../au-resto-formula';
import { AuRestoRecipeTypeAuResto, AuRestoRecipeTypeAuRestoService } from '../au-resto-recipe-type';
import { AuRestoOrderAuResto, AuRestoOrderAuRestoService } from '../au-resto-order';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-au-resto-recipe-au-resto-dialog',
    templateUrl: './au-resto-recipe-au-resto-dialog.component.html'
})
export class AuRestoRecipeAuRestoDialogComponent implements OnInit {

    auRestoRecipe: AuRestoRecipeAuResto;
    isSaving: boolean;

    aurestoformulas: AuRestoFormulaAuResto[];

    aurestorecipetypes: AuRestoRecipeTypeAuResto[];

    aurestoorders: AuRestoOrderAuResto[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auRestoRecipeService: AuRestoRecipeAuRestoService,
        private auRestoFormulaService: AuRestoFormulaAuRestoService,
        private auRestoRecipeTypeService: AuRestoRecipeTypeAuRestoService,
        private auRestoOrderService: AuRestoOrderAuRestoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.auRestoFormulaService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestoformulas = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.auRestoRecipeTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestorecipetypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.auRestoOrderService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestoorders = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.auRestoRecipe.id !== undefined) {
            this.subscribeToSaveResponse(
                this.auRestoRecipeService.update(this.auRestoRecipe));
        } else {
            this.subscribeToSaveResponse(
                this.auRestoRecipeService.create(this.auRestoRecipe));
        }
    }

    private subscribeToSaveResponse(result: Observable<AuRestoRecipeAuResto>) {
        result.subscribe((res: AuRestoRecipeAuResto) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AuRestoRecipeAuResto) {
        this.eventManager.broadcast({ name: 'auRestoRecipeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAuRestoFormulaById(index: number, item: AuRestoFormulaAuResto) {
        return item.id;
    }

    trackAuRestoRecipeTypeById(index: number, item: AuRestoRecipeTypeAuResto) {
        return item.id;
    }

    trackAuRestoOrderById(index: number, item: AuRestoOrderAuResto) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-au-resto-recipe-au-resto-popup',
    template: ''
})
export class AuRestoRecipeAuRestoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoRecipePopupService: AuRestoRecipeAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.auRestoRecipePopupService
                    .open(AuRestoRecipeAuRestoDialogComponent as Component, params['id']);
            } else {
                this.auRestoRecipePopupService
                    .open(AuRestoRecipeAuRestoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
