import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoProvinceAuResto } from './au-resto-province-au-resto.model';
import { AuRestoProvinceAuRestoPopupService } from './au-resto-province-au-resto-popup.service';
import { AuRestoProvinceAuRestoService } from './au-resto-province-au-resto.service';

@Component({
    selector: 'jhi-au-resto-province-au-resto-delete-dialog',
    templateUrl: './au-resto-province-au-resto-delete-dialog.component.html'
})
export class AuRestoProvinceAuRestoDeleteDialogComponent {

    auRestoProvince: AuRestoProvinceAuResto;

    constructor(
        private auRestoProvinceService: AuRestoProvinceAuRestoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auRestoProvinceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'auRestoProvinceListModification',
                content: 'Deleted an auRestoProvince'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-au-resto-province-au-resto-delete-popup',
    template: ''
})
export class AuRestoProvinceAuRestoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auRestoProvincePopupService: AuRestoProvinceAuRestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.auRestoProvincePopupService
                .open(AuRestoProvinceAuRestoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
