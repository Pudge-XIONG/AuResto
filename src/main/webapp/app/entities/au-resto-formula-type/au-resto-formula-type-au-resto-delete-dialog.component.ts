import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoFormulaTypeAuResto } from './au-resto-formula-type-au-resto.model';
import { AuRestoFormulaTypeAuRestoPopupService } from './au-resto-formula-type-au-resto-popup.service';
import { AuRestoFormulaTypeAuRestoService } from './au-resto-formula-type-au-resto.service';

@Component({
    selector: 'jhi-au-resto-formula-type-au-resto-delete-dialog',
    templateUrl: './au-resto-formula-type-au-resto-delete-dialog.component.html'
})
export class AuRestoFormulaTypeAuRestoDeleteDialogComponent {

    auRestoFormulaType: AuRestoFormulaTypeAuResto;

    constructor(
        private auRestoFormulaTypeService: AuRestoFormulaTypeAuRestoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auRestoFormulaTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'auRestoFormulaTypeListModification',
                content: 'Deleted an auRestoFormulaType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-au-resto-formula-type-au-resto-delete-popup',
    template: ''
})
export class AuRestoFormulaTypeAuRestoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoFormulaTypePopupService: AuRestoFormulaTypeAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.auRestoFormulaTypePopupService
                .open(AuRestoFormulaTypeAuRestoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
