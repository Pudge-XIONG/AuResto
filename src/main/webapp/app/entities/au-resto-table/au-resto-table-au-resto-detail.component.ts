import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoTableAuResto } from './au-resto-table-au-resto.model';
import { AuRestoTableAuRestoService } from './au-resto-table-au-resto.service';

@Component({
    selector: 'jhi-au-resto-table-au-resto-detail',
    templateUrl: './au-resto-table-au-resto-detail.component.html'
})
export class AuRestoTableAuRestoDetailComponent implements OnInit, OnDestroy {

    auRestoTable: AuRestoTableAuResto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private auRestoTableService: AuRestoTableAuRestoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuRestoTables();
    }

    load(id) {
        this.auRestoTableService.find(id).subscribe((auRestoTable) => {
            this.auRestoTable = auRestoTable;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuRestoTables() {
        this.eventSubscriber = this.eventManager.subscribe(
            'auRestoTableListModification',
            (response) => this.load(this.auRestoTable.id)
        );
    }
}
