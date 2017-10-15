import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoOrderTypeAuResto } from './au-resto-order-type-au-resto.model';
import { AuRestoOrderTypeAuRestoPopupService } from './au-resto-order-type-au-resto-popup.service';
import { AuRestoOrderTypeAuRestoService } from './au-resto-order-type-au-resto.service';

@Component({
    selector: 'jhi-au-resto-order-type-au-resto-delete-dialog',
    templateUrl: './au-resto-order-type-au-resto-delete-dialog.component.html'
})
export class AuRestoOrderTypeAuRestoDeleteDialogComponent {

    auRestoOrderType: AuRestoOrderTypeAuResto;

    constructor(
        private auRestoOrderTypeService: AuRestoOrderTypeAuRestoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auRestoOrderTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'auRestoOrderTypeListModification',
                content: 'Deleted an auRestoOrderType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-au-resto-order-type-au-resto-delete-popup',
    template: ''
})
export class AuRestoOrderTypeAuRestoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoOrderTypePopupService: AuRestoOrderTypeAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.auRestoOrderTypePopupService
                .open(AuRestoOrderTypeAuRestoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
