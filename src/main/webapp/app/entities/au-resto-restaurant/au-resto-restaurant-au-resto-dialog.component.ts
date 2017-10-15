import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { AuRestoRestaurantAuResto } from './au-resto-restaurant-au-resto.model';
import { AuRestoRestaurantAuRestoPopupService } from './au-resto-restaurant-au-resto-popup.service';
import { AuRestoRestaurantAuRestoService } from './au-resto-restaurant-au-resto.service';
import { AuRestoLocationAuResto, AuRestoLocationAuRestoService } from '../au-resto-location';
import { AuRestoUserAuResto, AuRestoUserAuRestoService } from '../au-resto-user';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-au-resto-restaurant-au-resto-dialog',
    templateUrl: './au-resto-restaurant-au-resto-dialog.component.html'
})
export class AuRestoRestaurantAuRestoDialogComponent implements OnInit {

    auRestoRestaurant: AuRestoRestaurantAuResto;
    isSaving: boolean;

    locations: AuRestoLocationAuResto[];

    aurestousers: AuRestoUserAuResto[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private auRestoRestaurantService: AuRestoRestaurantAuRestoService,
        private auRestoLocationService: AuRestoLocationAuRestoService,
        private auRestoUserService: AuRestoUserAuRestoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.auRestoLocationService
            .query({filter: 'aurestorestaurant-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.auRestoRestaurant.location || !this.auRestoRestaurant.location.id) {
                    this.locations = res.json;
                } else {
                    this.auRestoLocationService
                        .find(this.auRestoRestaurant.location.id)
                        .subscribe((subRes: AuRestoLocationAuResto) => {
                            this.locations = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.auRestoUserService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestousers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
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

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.auRestoRestaurant.id !== undefined) {
            this.subscribeToSaveResponse(
                this.auRestoRestaurantService.update(this.auRestoRestaurant));
        } else {
            this.subscribeToSaveResponse(
                this.auRestoRestaurantService.create(this.auRestoRestaurant));
        }
    }

    private subscribeToSaveResponse(result: Observable<AuRestoRestaurantAuResto>) {
        result.subscribe((res: AuRestoRestaurantAuResto) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AuRestoRestaurantAuResto) {
        this.eventManager.broadcast({ name: 'auRestoRestaurantListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAuRestoLocationById(index: number, item: AuRestoLocationAuResto) {
        return item.id;
    }

    trackAuRestoUserById(index: number, item: AuRestoUserAuResto) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-au-resto-restaurant-au-resto-popup',
    template: ''
})
export class AuRestoRestaurantAuRestoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoRestaurantPopupService: AuRestoRestaurantAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.auRestoRestaurantPopupService
                    .open(AuRestoRestaurantAuRestoDialogComponent as Component, params['id']);
            } else {
                this.auRestoRestaurantPopupService
                    .open(AuRestoRestaurantAuRestoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
