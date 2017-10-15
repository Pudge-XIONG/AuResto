import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoGenderAuResto } from './au-resto-gender-au-resto.model';
import { AuRestoGenderAuRestoService } from './au-resto-gender-au-resto.service';

@Component({
    selector: 'jhi-au-resto-gender-au-resto-detail',
    templateUrl: './au-resto-gender-au-resto-detail.component.html'
})
export class AuRestoGenderAuRestoDetailComponent implements OnInit, OnDestroy {

    auRestoGender: AuRestoGenderAuResto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private auRestoGenderService: AuRestoGenderAuRestoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuRestoGenders();
    }

    load(id) {
        this.auRestoGenderService.find(id).subscribe((auRestoGender) => {
            this.auRestoGender = auRestoGender;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuRestoGenders() {
        this.eventSubscriber = this.eventManager.subscribe(
            'auRestoGenderListModification',
            (response) => this.load(this.auRestoGender.id)
        );
    }
}
