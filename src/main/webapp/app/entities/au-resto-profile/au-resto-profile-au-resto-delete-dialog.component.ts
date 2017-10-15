import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoProfileAuResto } from './au-resto-profile-au-resto.model';
import { AuRestoProfileAuRestoPopupService } from './au-resto-profile-au-resto-popup.service';
import { AuRestoProfileAuRestoService } from './au-resto-profile-au-resto.service';

@Component({
    selector: 'jhi-au-resto-profile-au-resto-delete-dialog',
    templateUrl: './au-resto-profile-au-resto-delete-dialog.component.html'
})
export class AuRestoProfileAuRestoDeleteDialogComponent {

    auRestoProfile: AuRestoProfileAuResto;

    constructor(
        private auRestoProfileService: AuRestoProfileAuRestoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auRestoProfileService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'auRestoProfileListModification',
                content: 'Deleted an auRestoProfile'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-au-resto-profile-au-resto-delete-popup',
    template: ''
})
export class AuRestoProfileAuRestoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoProfilePopupService: AuRestoProfileAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.auRestoProfilePopupService
                .open(AuRestoProfileAuRestoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
