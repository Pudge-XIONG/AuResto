import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoCountryAuResto } from './au-resto-country-au-resto.model';
import { AuRestoCountryAuRestoService } from './au-resto-country-au-resto.service';

@Component({
    selector: 'jhi-au-resto-country-au-resto-detail',
    templateUrl: './au-resto-country-au-resto-detail.component.html'
})
export class AuRestoCountryAuRestoDetailComponent implements OnInit, OnDestroy {

    auRestoCountry: AuRestoCountryAuResto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private auRestoCountryService: AuRestoCountryAuRestoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuRestoCountries();
    }

    load(id) {
        this.auRestoCountryService.find(id).subscribe((auRestoCountry) => {
            this.auRestoCountry = auRestoCountry;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuRestoCountries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'auRestoCountryListModification',
            (response) => this.load(this.auRestoCountry.id)
        );
    }
}
