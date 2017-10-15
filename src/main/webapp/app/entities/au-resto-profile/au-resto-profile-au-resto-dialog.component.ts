import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuRestoProfileAuResto } from './au-resto-profile-au-resto.model';
import { AuRestoProfileAuRestoPopupService } from './au-resto-profile-au-resto-popup.service';
import { AuRestoProfileAuRestoService } from './au-resto-profile-au-resto.service';

@Component({
    selector: 'jhi-au-resto-profile-au-resto-dialog',
    templateUrl: './au-resto-profile-au-resto-dialog.component.html'
})
export class AuRestoProfileAuRestoDialogComponent implements OnInit {

    auRestoProfile: AuRestoProfileAuResto;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auRestoProfileService: AuRestoProfileAuRestoService,
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
        if (this.auRestoProfile.id !== undefined) {
            this.subscribeToSaveResponse(
                this.auRestoProfileService.update(this.auRestoProfile));
        } else {
            this.subscribeToSaveResponse(
                this.auRestoProfileService.create(this.auRestoProfile));
        }
    }

    private subscribeToSaveResponse(result: Observable<AuRestoProfileAuResto>) {
        result.subscribe((res: AuRestoProfileAuResto) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AuRestoProfileAuResto) {
        this.eventManager.broadcast({ name: 'auRestoProfileListModification', content: 'OK'});
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
    selector: 'jhi-au-resto-profile-au-resto-popup',
    template: ''
})
export class AuRestoProfileAuRestoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoProfilePopupService: AuRestoProfileAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.auRestoProfilePopupService
                    .open(AuRestoProfileAuRestoDialogComponent as Component, params['id']);
            } else {
                this.auRestoProfilePopupService
                    .open(AuRestoProfileAuRestoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
