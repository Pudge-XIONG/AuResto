import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoFormulaAuResto } from './au-resto-formula-au-resto.model';
import { AuRestoFormulaAuRestoPopupService } from './au-resto-formula-au-resto-popup.service';
import { AuRestoFormulaAuRestoService } from './au-resto-formula-au-resto.service';

@Component({
    selector: 'jhi-au-resto-formula-au-resto-delete-dialog',
    templateUrl: './au-resto-formula-au-resto-delete-dialog.component.html'
})
export class AuRestoFormulaAuRestoDeleteDialogComponent {

    auRestoFormula: AuRestoFormulaAuResto;

    constructor(
        private auRestoFormulaService: AuRestoFormulaAuRestoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auRestoFormulaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'auRestoFormulaListModification',
                content: 'Deleted an auRestoFormula'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-au-resto-formula-au-resto-delete-popup',
    template: ''
})
export class AuRestoFormulaAuRestoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoFormulaPopupService: AuRestoFormulaAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.auRestoFormulaPopupService
                .open(AuRestoFormulaAuRestoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
