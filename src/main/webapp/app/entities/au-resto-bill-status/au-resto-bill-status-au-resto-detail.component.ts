import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoBillStatusAuResto } from './au-resto-bill-status-au-resto.model';
import { AuRestoBillStatusAuRestoService } from './au-resto-bill-status-au-resto.service';

@Component({
    selector: 'jhi-au-resto-bill-status-au-resto-detail',
    templateUrl: './au-resto-bill-status-au-resto-detail.component.html'
})
export class AuRestoBillStatusAuRestoDetailComponent implements OnInit, OnDestroy {

    auRestoBillStatus: AuRestoBillStatusAuResto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private auRestoBillStatusService: AuRestoBillStatusAuRestoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuRestoBillStatuses();
    }

    load(id) {
        this.auRestoBillStatusService.find(id).subscribe((auRestoBillStatus) => {
            this.auRestoBillStatus = auRestoBillStatus;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuRestoBillStatuses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'auRestoBillStatusListModification',
            (response) => this.load(this.auRestoBillStatus.id)
        );
    }
}
