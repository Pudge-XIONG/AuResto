import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoOrderTypeAuResto } from './au-resto-order-type-au-resto.model';
import { AuRestoOrderTypeAuRestoService } from './au-resto-order-type-au-resto.service';

@Component({
    selector: 'jhi-au-resto-order-type-au-resto-detail',
    templateUrl: './au-resto-order-type-au-resto-detail.component.html'
})
export class AuRestoOrderTypeAuRestoDetailComponent implements OnInit, OnDestroy {

    auRestoOrderType: AuRestoOrderTypeAuResto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private auRestoOrderTypeService: AuRestoOrderTypeAuRestoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuRestoOrderTypes();
    }

    load(id) {
        this.auRestoOrderTypeService.find(id).subscribe((auRestoOrderType) => {
            this.auRestoOrderType = auRestoOrderType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuRestoOrderTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'auRestoOrderTypeListModification',
            (response) => this.load(this.auRestoOrderType.id)
        );
    }
}
