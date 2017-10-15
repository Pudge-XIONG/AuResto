import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoGenderAuResto } from './au-resto-gender-au-resto.model';
import { AuRestoGenderAuRestoPopupService } from './au-resto-gender-au-resto-popup.service';
import { AuRestoGenderAuRestoService } from './au-resto-gender-au-resto.service';

@Component({
    selector: 'jhi-au-resto-gender-au-resto-delete-dialog',
    templateUrl: './au-resto-gender-au-resto-delete-dialog.component.html'
})
export class AuRestoGenderAuRestoDeleteDialogComponent {

    auRestoGender: AuRestoGenderAuResto;

    constructor(
        private auRestoGenderService: AuRestoGenderAuRestoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auRestoGenderService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'auRestoGenderListModification',
                content: 'Deleted an auRestoGender'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-au-resto-gender-au-resto-delete-popup',
    template: ''
})
export class AuRestoGenderAuRestoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoGenderPopupService: AuRestoGenderAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.auRestoGenderPopupService
                .open(AuRestoGenderAuRestoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
