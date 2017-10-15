import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoProfileAuResto } from './au-resto-profile-au-resto.model';
import { AuRestoProfileAuRestoService } from './au-resto-profile-au-resto.service';

@Component({
    selector: 'jhi-au-resto-profile-au-resto-detail',
    templateUrl: './au-resto-profile-au-resto-detail.component.html'
})
export class AuRestoProfileAuRestoDetailComponent implements OnInit, OnDestroy {

    auRestoProfile: AuRestoProfileAuResto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private auRestoProfileService: AuRestoProfileAuRestoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuRestoProfiles();
    }

    load(id) {
        this.auRestoProfileService.find(id).subscribe((auRestoProfile) => {
            this.auRestoProfile = auRestoProfile;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuRestoProfiles() {
        this.eventSubscriber = this.eventManager.subscribe(
            'auRestoProfileListModification',
            (response) => this.load(this.auRestoProfile.id)
        );
    }
}
