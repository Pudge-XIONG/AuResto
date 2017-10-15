import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoRestaurantTypeAuResto } from './au-resto-restaurant-type-au-resto.model';
import { AuRestoRestaurantTypeAuRestoService } from './au-resto-restaurant-type-au-resto.service';

@Component({
    selector: 'jhi-au-resto-restaurant-type-au-resto-detail',
    templateUrl: './au-resto-restaurant-type-au-resto-detail.component.html'
})
export class AuRestoRestaurantTypeAuRestoDetailComponent implements OnInit, OnDestroy {

    auRestoRestaurantType: AuRestoRestaurantTypeAuResto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private auRestoRestaurantTypeService: AuRestoRestaurantTypeAuRestoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuRestoRestaurantTypes();
    }

    load(id) {
        this.auRestoRestaurantTypeService.find(id).subscribe((auRestoRestaurantType) => {
            this.auRestoRestaurantType = auRestoRestaurantType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuRestoRestaurantTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'auRestoRestaurantTypeListModification',
            (response) => this.load(this.auRestoRestaurantType.id)
        );
    }
}
