import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoFormulaTypeAuResto } from './au-resto-formula-type-au-resto.model';
import { AuRestoFormulaTypeAuRestoService } from './au-resto-formula-type-au-resto.service';

@Component({
    selector: 'jhi-au-resto-formula-type-au-resto-detail',
    templateUrl: './au-resto-formula-type-au-resto-detail.component.html'
})
export class AuRestoFormulaTypeAuRestoDetailComponent implements OnInit, OnDestroy {

    auRestoFormulaType: AuRestoFormulaTypeAuResto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private auRestoFormulaTypeService: AuRestoFormulaTypeAuRestoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuRestoFormulaTypes();
    }

    load(id) {
        this.auRestoFormulaTypeService.find(id).subscribe((auRestoFormulaType) => {
            this.auRestoFormulaType = auRestoFormulaType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuRestoFormulaTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'auRestoFormulaTypeListModification',
            (response) => this.load(this.auRestoFormulaType.id)
        );
    }
}
