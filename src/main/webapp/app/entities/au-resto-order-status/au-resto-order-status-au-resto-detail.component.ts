import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoOrderStatusAuResto } from './au-resto-order-status-au-resto.model';
import { AuRestoOrderStatusAuRestoService } from './au-resto-order-status-au-resto.service';

@Component({
    selector: 'jhi-au-resto-order-status-au-resto-detail',
    templateUrl: './au-resto-order-status-au-resto-detail.component.html'
})
export class AuRestoOrderStatusAuRestoDetailComponent implements OnInit, OnDestroy {

    auRestoOrderStatus: AuRestoOrderStatusAuResto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private auRestoOrderStatusService: AuRestoOrderStatusAuRestoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuRestoOrderStatuses();
    }

    load(id) {
        this.auRestoOrderStatusService.find(id).subscribe((auRestoOrderStatus) => {
            this.auRestoOrderStatus = auRestoOrderStatus;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuRestoOrderStatuses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'auRestoOrderStatusListModification',
            (response) => this.load(this.auRestoOrderStatus.id)
        );
    }
}
