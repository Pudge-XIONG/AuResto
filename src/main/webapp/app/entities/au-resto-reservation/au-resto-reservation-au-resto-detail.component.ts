import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoReservationAuResto } from './au-resto-reservation-au-resto.model';
import { AuRestoReservationAuRestoService } from './au-resto-reservation-au-resto.service';

@Component({
    selector: 'jhi-au-resto-reservation-au-resto-detail',
    templateUrl: './au-resto-reservation-au-resto-detail.component.html'
})
export class AuRestoReservationAuRestoDetailComponent implements OnInit, OnDestroy {

    auRestoReservation: AuRestoReservationAuResto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private auRestoReservationService: AuRestoReservationAuRestoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuRestoReservations();
    }

    load(id) {
        this.auRestoReservationService.find(id).subscribe((auRestoReservation) => {
            this.auRestoReservation = auRestoReservation;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuRestoReservations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'auRestoReservationListModification',
            (response) => this.load(this.auRestoReservation.id)
        );
    }
}
