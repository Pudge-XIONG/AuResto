import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoOrderAuResto } from './au-resto-order-au-resto.model';
import { AuRestoOrderAuRestoPopupService } from './au-resto-order-au-resto-popup.service';
import { AuRestoOrderAuRestoService } from './au-resto-order-au-resto.service';

@Component({
    selector: 'jhi-au-resto-order-au-resto-delete-dialog',
    templateUrl: './au-resto-order-au-resto-delete-dialog.component.html'
})
export class AuRestoOrderAuRestoDeleteDialogComponent {

    auRestoOrder: AuRestoOrderAuResto;

    constructor(
        private auRestoOrderService: AuRestoOrderAuRestoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auRestoOrderService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'auRestoOrderListModification',
                content: 'Deleted an auRestoOrder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-au-resto-order-au-resto-delete-popup',
    template: ''
})
export class AuRestoOrderAuRestoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoOrderPopupService: AuRestoOrderAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.auRestoOrderPopupService
                .open(AuRestoOrderAuRestoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
