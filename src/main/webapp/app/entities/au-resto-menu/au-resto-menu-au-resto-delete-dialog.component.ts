import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoMenuAuResto } from './au-resto-menu-au-resto.model';
import { AuRestoMenuAuRestoPopupService } from './au-resto-menu-au-resto-popup.service';
import { AuRestoMenuAuRestoService } from './au-resto-menu-au-resto.service';

@Component({
    selector: 'jhi-au-resto-menu-au-resto-delete-dialog',
    templateUrl: './au-resto-menu-au-resto-delete-dialog.component.html'
})
export class AuRestoMenuAuRestoDeleteDialogComponent {

    auRestoMenu: AuRestoMenuAuResto;

    constructor(
        private auRestoMenuService: AuRestoMenuAuRestoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auRestoMenuService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'auRestoMenuListModification',
                content: 'Deleted an auRestoMenu'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-au-resto-menu-au-resto-delete-popup',
    template: ''
})
export class AuRestoMenuAuRestoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoMenuPopupService: AuRestoMenuAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.auRestoMenuPopupService
                .open(AuRestoMenuAuRestoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
