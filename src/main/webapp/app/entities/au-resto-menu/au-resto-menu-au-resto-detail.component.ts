import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoMenuAuResto } from './au-resto-menu-au-resto.model';
import { AuRestoMenuAuRestoService } from './au-resto-menu-au-resto.service';

@Component({
    selector: 'jhi-au-resto-menu-au-resto-detail',
    templateUrl: './au-resto-menu-au-resto-detail.component.html'
})
export class AuRestoMenuAuRestoDetailComponent implements OnInit, OnDestroy {

    auRestoMenu: AuRestoMenuAuResto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private auRestoMenuService: AuRestoMenuAuRestoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuRestoMenus();
    }

    load(id) {
        this.auRestoMenuService.find(id).subscribe((auRestoMenu) => {
            this.auRestoMenu = auRestoMenu;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuRestoMenus() {
        this.eventSubscriber = this.eventManager.subscribe(
            'auRestoMenuListModification',
            (response) => this.load(this.auRestoMenu.id)
        );
    }
}
