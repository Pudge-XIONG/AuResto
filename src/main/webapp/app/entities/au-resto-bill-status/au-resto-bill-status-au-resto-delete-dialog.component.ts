import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoBillStatusAuResto } from './au-resto-bill-status-au-resto.model';
import { AuRestoBillStatusAuRestoPopupService } from './au-resto-bill-status-au-resto-popup.service';
import { AuRestoBillStatusAuRestoService } from './au-resto-bill-status-au-resto.service';

@Component({
    selector: 'jhi-au-resto-bill-status-au-resto-delete-dialog',
    templateUrl: './au-resto-bill-status-au-resto-delete-dialog.component.html'
})
export class AuRestoBillStatusAuRestoDeleteDialogComponent {

    auRestoBillStatus: AuRestoBillStatusAuResto;

    constructor(
        private auRestoBillStatusService: AuRestoBillStatusAuRestoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auRestoBillStatusService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'auRestoBillStatusListModification',
                content: 'Deleted an auRestoBillStatus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-au-resto-bill-status-au-resto-delete-popup',
    template: ''
})
export class AuRestoBillStatusAuRestoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoBillStatusPopupService: AuRestoBillStatusAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.auRestoBillStatusPopupService
                .open(AuRestoBillStatusAuRestoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
