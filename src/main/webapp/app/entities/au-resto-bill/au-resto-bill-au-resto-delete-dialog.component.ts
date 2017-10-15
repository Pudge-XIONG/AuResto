import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoBillAuResto } from './au-resto-bill-au-resto.model';
import { AuRestoBillAuRestoPopupService } from './au-resto-bill-au-resto-popup.service';
import { AuRestoBillAuRestoService } from './au-resto-bill-au-resto.service';

@Component({
    selector: 'jhi-au-resto-bill-au-resto-delete-dialog',
    templateUrl: './au-resto-bill-au-resto-delete-dialog.component.html'
})
export class AuRestoBillAuRestoDeleteDialogComponent {

    auRestoBill: AuRestoBillAuResto;

    constructor(
        private auRestoBillService: AuRestoBillAuRestoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auRestoBillService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'auRestoBillListModification',
                content: 'Deleted an auRestoBill'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-au-resto-bill-au-resto-delete-popup',
    template: ''
})
export class AuRestoBillAuRestoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoBillPopupService: AuRestoBillAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.auRestoBillPopupService
                .open(AuRestoBillAuRestoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
