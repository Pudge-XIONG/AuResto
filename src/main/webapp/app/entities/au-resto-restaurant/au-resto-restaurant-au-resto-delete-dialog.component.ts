import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoRestaurantAuResto } from './au-resto-restaurant-au-resto.model';
import { AuRestoRestaurantAuRestoPopupService } from './au-resto-restaurant-au-resto-popup.service';
import { AuRestoRestaurantAuRestoService } from './au-resto-restaurant-au-resto.service';

@Component({
    selector: 'jhi-au-resto-restaurant-au-resto-delete-dialog',
    templateUrl: './au-resto-restaurant-au-resto-delete-dialog.component.html'
})
export class AuRestoRestaurantAuRestoDeleteDialogComponent {

    auRestoRestaurant: AuRestoRestaurantAuResto;

    constructor(
        private auRestoRestaurantService: AuRestoRestaurantAuRestoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auRestoRestaurantService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'auRestoRestaurantListModification',
                content: 'Deleted an auRestoRestaurant'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-au-resto-restaurant-au-resto-delete-popup',
    template: ''
})
export class AuRestoRestaurantAuRestoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoRestaurantPopupService: AuRestoRestaurantAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.auRestoRestaurantPopupService
                .open(AuRestoRestaurantAuRestoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
