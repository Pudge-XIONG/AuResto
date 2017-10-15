import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoRecipeTypeAuResto } from './au-resto-recipe-type-au-resto.model';
import { AuRestoRecipeTypeAuRestoPopupService } from './au-resto-recipe-type-au-resto-popup.service';
import { AuRestoRecipeTypeAuRestoService } from './au-resto-recipe-type-au-resto.service';

@Component({
    selector: 'jhi-au-resto-recipe-type-au-resto-delete-dialog',
    templateUrl: './au-resto-recipe-type-au-resto-delete-dialog.component.html'
})
export class AuRestoRecipeTypeAuRestoDeleteDialogComponent {

    auRestoRecipeType: AuRestoRecipeTypeAuResto;

    constructor(
        private auRestoRecipeTypeService: AuRestoRecipeTypeAuRestoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auRestoRecipeTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'auRestoRecipeTypeListModification',
                content: 'Deleted an auRestoRecipeType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-au-resto-recipe-type-au-resto-delete-popup',
    template: ''
})
export class AuRestoRecipeTypeAuRestoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoRecipeTypePopupService: AuRestoRecipeTypeAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.auRestoRecipeTypePopupService
                .open(AuRestoRecipeTypeAuRestoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
