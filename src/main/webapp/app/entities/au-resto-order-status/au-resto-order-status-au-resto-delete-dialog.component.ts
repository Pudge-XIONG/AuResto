import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoOrderStatusAuResto } from './au-resto-order-status-au-resto.model';
import { AuRestoOrderStatusAuRestoPopupService } from './au-resto-order-status-au-resto-popup.service';
import { AuRestoOrderStatusAuRestoService } from './au-resto-order-status-au-resto.service';

@Component({
    selector: 'jhi-au-resto-order-status-au-resto-delete-dialog',
    templateUrl: './au-resto-order-status-au-resto-delete-dialog.component.html'
})
export class AuRestoOrderStatusAuRestoDeleteDialogComponent {

    auRestoOrderStatus: AuRestoOrderStatusAuResto;

    constructor(
        private auRestoOrderStatusService: AuRestoOrderStatusAuRestoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auRestoOrderStatusService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'auRestoOrderStatusListModification',
                content: 'Deleted an auRestoOrderStatus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-au-resto-order-status-au-resto-delete-popup',
    template: ''
})
export class AuRestoOrderStatusAuRestoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoOrderStatusPopupService: AuRestoOrderStatusAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.auRestoOrderStatusPopupService
                .open(AuRestoOrderStatusAuRestoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
