import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoCityAuResto } from './au-resto-city-au-resto.model';
import { AuRestoCityAuRestoService } from './au-resto-city-au-resto.service';

@Component({
    selector: 'jhi-au-resto-city-au-resto-detail',
    templateUrl: './au-resto-city-au-resto-detail.component.html'
})
export class AuRestoCityAuRestoDetailComponent implements OnInit, OnDestroy {

    auRestoCity: AuRestoCityAuResto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private auRestoCityService: AuRestoCityAuRestoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuRestoCities();
    }

    load(id) {
        this.auRestoCityService.find(id).subscribe((auRestoCity) => {
            this.auRestoCity = auRestoCity;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuRestoCities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'auRestoCityListModification',
            (response) => this.load(this.auRestoCity.id)
        );
    }
}
