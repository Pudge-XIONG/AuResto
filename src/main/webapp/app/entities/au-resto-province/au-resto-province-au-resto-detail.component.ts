import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoProvinceAuResto } from './au-resto-province-au-resto.model';
import { AuRestoProvinceAuRestoService } from './au-resto-province-au-resto.service';

@Component({
    selector: 'jhi-au-resto-province-au-resto-detail',
    templateUrl: './au-resto-province-au-resto-detail.component.html'
})
export class AuRestoProvinceAuRestoDetailComponent implements OnInit, OnDestroy {

    auRestoProvince: AuRestoProvinceAuResto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private auRestoProvinceService: AuRestoProvinceAuRestoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuRestoProvinces();
    }

    load(id) {
        this.auRestoProvinceService.find(id).subscribe((auRestoProvince) => {
            this.auRestoProvince = auRestoProvince;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuRestoProvinces() {
        this.eventSubscriber = this.eventManager.subscribe(
            'auRestoProvinceListModification',
            (response) => this.load(this.auRestoProvince.id)
        );
    }
}
