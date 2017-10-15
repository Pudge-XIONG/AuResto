import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoReservationAuResto } from './au-resto-reservation-au-resto.model';
import { AuRestoReservationAuRestoPopupService } from './au-resto-reservation-au-resto-popup.service';
import { AuRestoReservationAuRestoService } from './au-resto-reservation-au-resto.service';

@Component({
    selector: 'jhi-au-resto-reservation-au-resto-delete-dialog',
    templateUrl: './au-resto-reservation-au-resto-delete-dialog.component.html'
})
export class AuRestoReservationAuRestoDeleteDialogComponent {

    auRestoReservation: AuRestoReservationAuResto;

    constructor(
        private auRestoReservationService: AuRestoReservationAuRestoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auRestoReservationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'auRestoReservationListModification',
                content: 'Deleted an auRestoReservation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-au-resto-reservation-au-resto-delete-popup',
    template: ''
})
export class AuRestoReservationAuRestoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoReservationPopupService: AuRestoReservationAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.auRestoReservationPopupService
                .open(AuRestoReservationAuRestoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
