import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoLocationAuResto } from './au-resto-location-au-resto.model';
import { AuRestoLocationAuRestoService } from './au-resto-location-au-resto.service';

@Component({
    selector: 'jhi-au-resto-location-au-resto-detail',
    templateUrl: './au-resto-location-au-resto-detail.component.html'
})
export class AuRestoLocationAuRestoDetailComponent implements OnInit, OnDestroy {

    auRestoLocation: AuRestoLocationAuResto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private auRestoLocationService: AuRestoLocationAuRestoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuRestoLocations();
    }

    load(id) {
        this.auRestoLocationService.find(id).subscribe((auRestoLocation) => {
            this.auRestoLocation = auRestoLocation;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuRestoLocations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'auRestoLocationListModification',
            (response) => this.load(this.auRestoLocation.id)
        );
    }
}
