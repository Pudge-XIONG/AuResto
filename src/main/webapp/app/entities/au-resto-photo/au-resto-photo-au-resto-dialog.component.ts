import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { AuRestoPhotoAuResto } from './au-resto-photo-au-resto.model';
import { AuRestoPhotoAuRestoPopupService } from './au-resto-photo-au-resto-popup.service';
import { AuRestoPhotoAuRestoService } from './au-resto-photo-au-resto.service';
import { AuRestoRestaurantAuResto, AuRestoRestaurantAuRestoService } from '../au-resto-restaurant';
import { AuRestoMenuAuResto, AuRestoMenuAuRestoService } from '../au-resto-menu';
import { AuRestoFormulaAuResto, AuRestoFormulaAuRestoService } from '../au-resto-formula';
import { AuRestoRecipeAuResto, AuRestoRecipeAuRestoService } from '../au-resto-recipe';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-au-resto-photo-au-resto-dialog',
    templateUrl: './au-resto-photo-au-resto-dialog.component.html'
})
export class AuRestoPhotoAuRestoDialogComponent implements OnInit {

    auRestoPhoto: AuRestoPhotoAuResto;
    isSaving: boolean;

    aurestorestaurants: AuRestoRestaurantAuResto[];

    aurestomenus: AuRestoMenuAuResto[];

    aurestoformulas: AuRestoFormulaAuResto[];

    aurestorecipes: AuRestoRecipeAuResto[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private auRestoPhotoService: AuRestoPhotoAuRestoService,
        private auRestoRestaurantService: AuRestoRestaurantAuRestoService,
        private auRestoMenuService: AuRestoMenuAuRestoService,
        private auRestoFormulaService: AuRestoFormulaAuRestoService,
        private auRestoRecipeService: AuRestoRecipeAuRestoService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.auRestoRestaurantService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestorestaurants = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.auRestoMenuService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestomenus = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.auRestoFormulaService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestoformulas = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.auRestoRecipeService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestorecipes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.auRestoPhoto, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.auRestoPhoto.id !== undefined) {
            this.subscribeToSaveResponse(
                this.auRestoPhotoService.update(this.auRestoPhoto));
        } else {
            this.subscribeToSaveResponse(
                this.auRestoPhotoService.create(this.auRestoPhoto));
        }
    }

    private subscribeToSaveResponse(result: Observable<AuRestoPhotoAuResto>) {
        result.subscribe((res: AuRestoPhotoAuResto) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AuRestoPhotoAuResto) {
        this.eventManager.broadcast({ name: 'auRestoPhotoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAuRestoRestaurantById(index: number, item: AuRestoRestaurantAuResto) {
        return item.id;
    }

    trackAuRestoMenuById(index: number, item: AuRestoMenuAuResto) {
        return item.id;
    }

    trackAuRestoFormulaById(index: number, item: AuRestoFormulaAuResto) {
        return item.id;
    }

    trackAuRestoRecipeById(index: number, item: AuRestoRecipeAuResto) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-au-resto-photo-au-resto-popup',
    template: ''
})
export class AuRestoPhotoAuRestoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoPhotoPopupService: AuRestoPhotoAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.auRestoPhotoPopupService
                    .open(AuRestoPhotoAuRestoDialogComponent as Component, params['id']);
            } else {
                this.auRestoPhotoPopupService
                    .open(AuRestoPhotoAuRestoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
