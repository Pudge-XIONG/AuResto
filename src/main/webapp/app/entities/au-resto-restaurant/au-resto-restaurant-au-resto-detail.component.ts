import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { AuRestoRestaurantAuResto } from './au-resto-restaurant-au-resto.model';
import { AuRestoRestaurantAuRestoService } from './au-resto-restaurant-au-resto.service';

@Component({
    selector: 'jhi-au-resto-restaurant-au-resto-detail',
    templateUrl: './au-resto-restaurant-au-resto-detail.component.html'
})
export class AuRestoRestaurantAuRestoDetailComponent implements OnInit, OnDestroy {

    auRestoRestaurant: AuRestoRestaurantAuResto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private auRestoRestaurantService: AuRestoRestaurantAuRestoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuRestoRestaurants();
    }

    load(id) {
        this.auRestoRestaurantService.find(id).subscribe((auRestoRestaurant) => {
            this.auRestoRestaurant = auRestoRestaurant;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuRestoRestaurants() {
        this.eventSubscriber = this.eventManager.subscribe(
            'auRestoRestaurantListModification',
            (response) => this.load(this.auRestoRestaurant.id)
        );
    }
}
