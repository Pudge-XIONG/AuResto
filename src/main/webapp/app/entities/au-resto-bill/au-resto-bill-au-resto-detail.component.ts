import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoBillAuResto } from './au-resto-bill-au-resto.model';
import { AuRestoBillAuRestoService } from './au-resto-bill-au-resto.service';

@Component({
    selector: 'jhi-au-resto-bill-au-resto-detail',
    templateUrl: './au-resto-bill-au-resto-detail.component.html'
})
export class AuRestoBillAuRestoDetailComponent implements OnInit, OnDestroy {

    auRestoBill: AuRestoBillAuResto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private auRestoBillService: AuRestoBillAuRestoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuRestoBills();
    }

    load(id) {
        this.auRestoBillService.find(id).subscribe((auRestoBill) => {
            this.auRestoBill = auRestoBill;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuRestoBills() {
        this.eventSubscriber = this.eventManager.subscribe(
            'auRestoBillListModification',
            (response) => this.load(this.auRestoBill.id)
        );
    }
}
