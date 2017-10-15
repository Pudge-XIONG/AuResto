import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoTableAuResto } from './au-resto-table-au-resto.model';
import { AuRestoTableAuRestoPopupService } from './au-resto-table-au-resto-popup.service';
import { AuRestoTableAuRestoService } from './au-resto-table-au-resto.service';

@Component({
    selector: 'jhi-au-resto-table-au-resto-delete-dialog',
    templateUrl: './au-resto-table-au-resto-delete-dialog.component.html'
})
export class AuRestoTableAuRestoDeleteDialogComponent {

    auRestoTable: AuRestoTableAuResto;

    constructor(
        private auRestoTableService: AuRestoTableAuRestoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auRestoTableService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'auRestoTableListModification',
                content: 'Deleted an auRestoTable'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-au-resto-table-au-resto-delete-popup',
    template: ''
})
export class AuRestoTableAuRestoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoTablePopupService: AuRestoTableAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.auRestoTablePopupService
                .open(AuRestoTableAuRestoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
