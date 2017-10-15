import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoLocationAuResto } from './au-resto-location-au-resto.model';
import { AuRestoLocationAuRestoPopupService } from './au-resto-location-au-resto-popup.service';
import { AuRestoLocationAuRestoService } from './au-resto-location-au-resto.service';

@Component({
    selector: 'jhi-au-resto-location-au-resto-delete-dialog',
    templateUrl: './au-resto-location-au-resto-delete-dialog.component.html'
})
export class AuRestoLocationAuRestoDeleteDialogComponent {

    auRestoLocation: AuRestoLocationAuResto;

    constructor(
        private auRestoLocationService: AuRestoLocationAuRestoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auRestoLocationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'auRestoLocationListModification',
                content: 'Deleted an auRestoLocation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-au-resto-location-au-resto-delete-popup',
    template: ''
})
export class AuRestoLocationAuRestoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoLocationPopupService: AuRestoLocationAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.auRestoLocationPopupService
                .open(AuRestoLocationAuRestoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
