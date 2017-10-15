import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoRecipeAuResto } from './au-resto-recipe-au-resto.model';
import { AuRestoRecipeAuRestoPopupService } from './au-resto-recipe-au-resto-popup.service';
import { AuRestoRecipeAuRestoService } from './au-resto-recipe-au-resto.service';

@Component({
    selector: 'jhi-au-resto-recipe-au-resto-delete-dialog',
    templateUrl: './au-resto-recipe-au-resto-delete-dialog.component.html'
})
export class AuRestoRecipeAuRestoDeleteDialogComponent {

    auRestoRecipe: AuRestoRecipeAuResto;

    constructor(
        private auRestoRecipeService: AuRestoRecipeAuRestoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auRestoRecipeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'auRestoRecipeListModification',
                content: 'Deleted an auRestoRecipe'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-au-resto-recipe-au-resto-delete-popup',
    template: ''
})
export class AuRestoRecipeAuRestoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoRecipePopupService: AuRestoRecipeAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.auRestoRecipePopupService
                .open(AuRestoRecipeAuRestoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
