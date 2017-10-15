import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoPhotoAuResto } from './au-resto-photo-au-resto.model';
import { AuRestoPhotoAuRestoPopupService } from './au-resto-photo-au-resto-popup.service';
import { AuRestoPhotoAuRestoService } from './au-resto-photo-au-resto.service';

@Component({
    selector: 'jhi-au-resto-photo-au-resto-delete-dialog',
    templateUrl: './au-resto-photo-au-resto-delete-dialog.component.html'
})
export class AuRestoPhotoAuRestoDeleteDialogComponent {

    auRestoPhoto: AuRestoPhotoAuResto;

    constructor(
        private auRestoPhotoService: AuRestoPhotoAuRestoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auRestoPhotoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'auRestoPhotoListModification',
                content: 'Deleted an auRestoPhoto'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-au-resto-photo-au-resto-delete-popup',
    template: ''
})
export class AuRestoPhotoAuRestoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoPhotoPopupService: AuRestoPhotoAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.auRestoPhotoPopupService
                .open(AuRestoPhotoAuRestoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
