import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoCountryAuResto } from './au-resto-country-au-resto.model';
import { AuRestoCountryAuRestoPopupService } from './au-resto-country-au-resto-popup.service';
import { AuRestoCountryAuRestoService } from './au-resto-country-au-resto.service';

@Component({
    selector: 'jhi-au-resto-country-au-resto-delete-dialog',
    templateUrl: './au-resto-country-au-resto-delete-dialog.component.html'
})
export class AuRestoCountryAuRestoDeleteDialogComponent {

    auRestoCountry: AuRestoCountryAuResto;

    constructor(
        private auRestoCountryService: AuRestoCountryAuRestoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auRestoCountryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'auRestoCountryListModification',
                content: 'Deleted an auRestoCountry'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-au-resto-country-au-resto-delete-popup',
    template: ''
})
export class AuRestoCountryAuRestoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoCountryPopupService: AuRestoCountryAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.auRestoCountryPopupService
                .open(AuRestoCountryAuRestoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
