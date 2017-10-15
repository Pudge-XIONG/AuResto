import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoFormulaAuResto } from './au-resto-formula-au-resto.model';
import { AuRestoFormulaAuRestoService } from './au-resto-formula-au-resto.service';

@Component({
    selector: 'jhi-au-resto-formula-au-resto-detail',
    templateUrl: './au-resto-formula-au-resto-detail.component.html'
})
export class AuRestoFormulaAuRestoDetailComponent implements OnInit, OnDestroy {

    auRestoFormula: AuRestoFormulaAuResto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private auRestoFormulaService: AuRestoFormulaAuRestoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuRestoFormulas();
    }

    load(id) {
        this.auRestoFormulaService.find(id).subscribe((auRestoFormula) => {
            this.auRestoFormula = auRestoFormula;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuRestoFormulas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'auRestoFormulaListModification',
            (response) => this.load(this.auRestoFormula.id)
        );
    }
}
