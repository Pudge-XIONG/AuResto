import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuRestoRecipeAuResto } from './au-resto-recipe-au-resto.model';
import { AuRestoRecipeAuRestoService } from './au-resto-recipe-au-resto.service';

@Component({
    selector: 'jhi-au-resto-recipe-au-resto-detail',
    templateUrl: './au-resto-recipe-au-resto-detail.component.html'
})
export class AuRestoRecipeAuRestoDetailComponent implements OnInit, OnDestroy {

    auRestoRecipe: AuRestoRecipeAuResto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private auRestoRecipeService: AuRestoRecipeAuRestoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuRestoRecipes();
    }

    load(id) {
        this.auRestoRecipeService.find(id).subscribe((auRestoRecipe) => {
            this.auRestoRecipe = auRestoRecipe;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuRestoRecipes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'auRestoRecipeListModification',
            (response) => this.load(this.auRestoRecipe.id)
        );
    }
}
