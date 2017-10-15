import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoRecipeTypeAuResto } from './au-resto-recipe-type-au-resto.model';
import { AuRestoRecipeTypeAuRestoService } from './au-resto-recipe-type-au-resto.service';

@Component({
    selector: 'jhi-au-resto-recipe-type-au-resto-detail',
    templateUrl: './au-resto-recipe-type-au-resto-detail.component.html'
})
export class AuRestoRecipeTypeAuRestoDetailComponent implements OnInit, OnDestroy {

    auRestoRecipeType: AuRestoRecipeTypeAuResto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private auRestoRecipeTypeService: AuRestoRecipeTypeAuRestoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuRestoRecipeTypes();
    }

    load(id) {
        this.auRestoRecipeTypeService.find(id).subscribe((auRestoRecipeType) => {
            this.auRestoRecipeType = auRestoRecipeType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuRestoRecipeTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'auRestoRecipeTypeListModification',
            (response) => this.load(this.auRestoRecipeType.id)
        );
    }
}
