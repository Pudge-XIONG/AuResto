import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoCityAuResto } from './au-resto-city-au-resto.model';
import { AuRestoCityAuRestoPopupService } from './au-resto-city-au-resto-popup.service';
import { AuRestoCityAuRestoService } from './au-resto-city-au-resto.service';

@Component({
    selector: 'jhi-au-resto-city-au-resto-delete-dialog',
    templateUrl: './au-resto-city-au-resto-delete-dialog.component.html'
})
export class AuRestoCityAuRestoDeleteDialogComponent {

    auRestoCity: AuRestoCityAuResto;

    constructor(
        private auRestoCityService: AuRestoCityAuRestoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auRestoCityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'auRestoCityListModification',
                content: 'Deleted an auRestoCity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-au-resto-city-au-resto-delete-popup',
    template: ''
})
export class AuRestoCityAuRestoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoCityPopupService: AuRestoCityAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.auRestoCityPopupService
                .open(AuRestoCityAuRestoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
