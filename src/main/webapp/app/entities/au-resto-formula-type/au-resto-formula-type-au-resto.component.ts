import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { AuRestoFormulaTypeAuResto } from './au-resto-formula-type-au-resto.model';
import { AuRestoFormulaTypeAuRestoService } from './au-resto-formula-type-au-resto.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-au-resto-formula-type-au-resto',
    templateUrl: './au-resto-formula-type-au-resto.component.html'
})
export class AuRestoFormulaTypeAuRestoComponent implements OnInit, OnDestroy {
auRestoFormulaTypes: AuRestoFormulaTypeAuResto[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private auRestoFormulaTypeService: AuRestoFormulaTypeAuRestoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.auRestoFormulaTypeService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.auRestoFormulaTypes = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.auRestoFormulaTypeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.auRestoFormulaTypes = res.json;
                this.currentSearch = '';
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAuRestoFormulaTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AuRestoFormulaTypeAuResto) {
        return item.id;
    }
    registerChangeInAuRestoFormulaTypes() {
        this.eventSubscriber = this.eventManager.subscribe('auRestoFormulaTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
