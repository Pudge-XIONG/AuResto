import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuRestoUserAuResto } from './au-resto-user-au-resto.model';
import { AuRestoUserAuRestoPopupService } from './au-resto-user-au-resto-popup.service';
import { AuRestoUserAuRestoService } from './au-resto-user-au-resto.service';
import { AuRestoPhotoAuResto, AuRestoPhotoAuRestoService } from '../au-resto-photo';
import { AuRestoGenderAuResto, AuRestoGenderAuRestoService } from '../au-resto-gender';
import { AuRestoProfileAuResto, AuRestoProfileAuRestoService } from '../au-resto-profile';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-au-resto-user-au-resto-dialog',
    templateUrl: './au-resto-user-au-resto-dialog.component.html'
})
export class AuRestoUserAuRestoDialogComponent implements OnInit {

    auRestoUser: AuRestoUserAuResto;
    isSaving: boolean;

    photos: AuRestoPhotoAuResto[];

    aurestogenders: AuRestoGenderAuResto[];

    aurestoprofiles: AuRestoProfileAuResto[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auRestoUserService: AuRestoUserAuRestoService,
        private auRestoPhotoService: AuRestoPhotoAuRestoService,
        private auRestoGenderService: AuRestoGenderAuRestoService,
        private auRestoProfileService: AuRestoProfileAuRestoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.auRestoPhotoService
            .query({filter: 'aurestouser-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.auRestoUser.photo || !this.auRestoUser.photo.id) {
                    this.photos = res.json;
                } else {
                    this.auRestoPhotoService
                        .find(this.auRestoUser.photo.id)
                        .subscribe((subRes: AuRestoPhotoAuResto) => {
                            this.photos = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.auRestoGenderService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestogenders = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.auRestoProfileService.query()
            .subscribe((res: ResponseWrapper) => { this.aurestoprofiles = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.auRestoUser.id !== undefined) {
            this.subscribeToSaveResponse(
                this.auRestoUserService.update(this.auRestoUser));
        } else {
            this.subscribeToSaveResponse(
                this.auRestoUserService.create(this.auRestoUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<AuRestoUserAuResto>) {
        result.subscribe((res: AuRestoUserAuResto) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AuRestoUserAuResto) {
        this.eventManager.broadcast({ name: 'auRestoUserListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAuRestoPhotoById(index: number, item: AuRestoPhotoAuResto) {
        return item.id;
    }

    trackAuRestoGenderById(index: number, item: AuRestoGenderAuResto) {
        return item.id;
    }

    trackAuRestoProfileById(index: number, item: AuRestoProfileAuResto) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-au-resto-user-au-resto-popup',
    template: ''
})
export class AuRestoUserAuRestoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoUserPopupService: AuRestoUserAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.auRestoUserPopupService
                    .open(AuRestoUserAuRestoDialogComponent as Component, params['id']);
            } else {
                this.auRestoUserPopupService
                    .open(AuRestoUserAuRestoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
