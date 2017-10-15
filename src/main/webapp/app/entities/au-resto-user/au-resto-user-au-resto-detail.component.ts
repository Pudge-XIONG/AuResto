import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoUserAuResto } from './au-resto-user-au-resto.model';
import { AuRestoUserAuRestoService } from './au-resto-user-au-resto.service';

@Component({
    selector: 'jhi-au-resto-user-au-resto-detail',
    templateUrl: './au-resto-user-au-resto-detail.component.html'
})
export class AuRestoUserAuRestoDetailComponent implements OnInit, OnDestroy {

    auRestoUser: AuRestoUserAuResto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private auRestoUserService: AuRestoUserAuRestoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuRestoUsers();
    }

    load(id) {
        this.auRestoUserService.find(id).subscribe((auRestoUser) => {
            this.auRestoUser = auRestoUser;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuRestoUsers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'auRestoUserListModification',
            (response) => this.load(this.auRestoUser.id)
        );
    }
}
