import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoUserAuResto } from './au-resto-user-au-resto.model';
import { AuRestoUserAuRestoPopupService } from './au-resto-user-au-resto-popup.service';
import { AuRestoUserAuRestoService } from './au-resto-user-au-resto.service';

@Component({
    selector: 'jhi-au-resto-user-au-resto-delete-dialog',
    templateUrl: './au-resto-user-au-resto-delete-dialog.component.html'
})
export class AuRestoUserAuRestoDeleteDialogComponent {

    auRestoUser: AuRestoUserAuResto;

    constructor(
        private auRestoUserService: AuRestoUserAuRestoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auRestoUserService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'auRestoUserListModification',
                content: 'Deleted an auRestoUser'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-au-resto-user-au-resto-delete-popup',
    template: ''
})
export class AuRestoUserAuRestoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoUserPopupService: AuRestoUserAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.auRestoUserPopupService
                .open(AuRestoUserAuRestoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
