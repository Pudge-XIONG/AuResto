import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuRestoMenuAuResto } from './au-resto-menu-au-resto.model';
import { AuRestoMenuAuRestoPopupService } from './au-resto-menu-au-resto-popup.service';
import { AuRestoMenuAuRestoService } from './au-resto-menu-au-resto.service';
import { AuRestoRestaurantAuResto, AuRestoRestaurantAuRestoService } from '../au-resto-restaurant';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-au-resto-menu-au-resto-dialog',
    templateUrl: './au-resto-menu-au-resto-dialog.component.html'
})
export class AuRestoMenuAuRestoDialogComponent implements OnInit {

    auRestoMenu: AuRestoMenuAuResto;
    isSaving: boolean;

    aurestorestaurants: AuRestoRestaurantAuResto[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auRestoMenuService: AuRestoMenuAuRestoService,
        private auRestoRestaurantService: AuRestoRestaurantAuRestoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.auRestoRestaurantService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestorestaurants = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.auRestoMenu.id !== undefined) {
            this.subscribeToSaveResponse(
                this.auRestoMenuService.update(this.auRestoMenu));
        } else {
            this.subscribeToSaveResponse(
                this.auRestoMenuService.create(this.auRestoMenu));
        }
    }

    private subscribeToSaveResponse(result: Observable<AuRestoMenuAuResto>) {
        result.subscribe((res: AuRestoMenuAuResto) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AuRestoMenuAuResto) {
        this.eventManager.broadcast({ name: 'auRestoMenuListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-au-resto-menu-au-resto-popup',
    template: ''
})
export class AuRestoMenuAuRestoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoMenuPopupService: AuRestoMenuAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.auRestoMenuPopupService
                    .open(AuRestoMenuAuRestoDialogComponent as Component, params['id']);
            } else {
                this.auRestoMenuPopupService
                    .open(AuRestoMenuAuRestoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
