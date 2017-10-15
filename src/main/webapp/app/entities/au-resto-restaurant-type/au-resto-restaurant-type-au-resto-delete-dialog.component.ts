import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoRestaurantTypeAuResto } from './au-resto-restaurant-type-au-resto.model';
import { AuRestoRestaurantTypeAuRestoPopupService } from './au-resto-restaurant-type-au-resto-popup.service';
import { AuRestoRestaurantTypeAuRestoService } from './au-resto-restaurant-type-au-resto.service';

@Component({
    selector: 'jhi-au-resto-restaurant-type-au-resto-delete-dialog',
    templateUrl: './au-resto-restaurant-type-au-resto-delete-dialog.component.html'
})
export class AuRestoRestaurantTypeAuRestoDeleteDialogComponent {

    auRestoRestaurantType: AuRestoRestaurantTypeAuResto;

    constructor(
        private auRestoRestaurantTypeService: AuRestoRestaurantTypeAuRestoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auRestoRestaurantTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'auRestoRestaurantTypeListModification',
                content: 'Deleted an auRestoRestaurantType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-au-resto-restaurant-type-au-resto-delete-popup',
    template: ''
})
export class AuRestoRestaurantTypeAuRestoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoRestaurantTypePopupService: AuRestoRestaurantTypeAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.auRestoRestaurantTypePopupService
                .open(AuRestoRestaurantTypeAuRestoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
