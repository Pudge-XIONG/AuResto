import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoOrderAuResto } from './au-resto-order-au-resto.model';
import { AuRestoOrderAuRestoService } from './au-resto-order-au-resto.service';

@Component({
    selector: 'jhi-au-resto-order-au-resto-detail',
    templateUrl: './au-resto-order-au-resto-detail.component.html'
})
export class AuRestoOrderAuRestoDetailComponent implements OnInit, OnDestroy {

    auRestoOrder: AuRestoOrderAuResto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private auRestoOrderService: AuRestoOrderAuRestoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuRestoOrders();
    }

    load(id) {
        this.auRestoOrderService.find(id).subscribe((auRestoOrder) => {
            this.auRestoOrder = auRestoOrder;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuRestoOrders() {
        this.eventSubscriber = this.eventManager.subscribe(
            'auRestoOrderListModification',
            (response) => this.load(this.auRestoOrder.id)
        );
    }
}
