import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { AuRestoProvinceAuResto } from './au-resto-province-au-resto.model';
import { AuRestoProvinceAuRestoService } from './au-resto-province-au-resto.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-au-resto-province-au-resto',
    templateUrl: './au-resto-province-au-resto.component.html'
})
export class AuRestoProvinceAuRestoComponent implements OnInit, OnDestroy {
auRestoProvinces: AuRestoProvinceAuResto[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private auRestoProvinceService: AuRestoProvinceAuRestoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.auRestoProvinceService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.auRestoProvinces = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.auRestoProvinceService.query().subscribe(
            (res: ResponseWrapper) => {
                this.auRestoProvinces = res.json;
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
        this.registerChangeInAuRestoProvinces();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AuRestoProvinceAuResto) {
        return item.id;
    }
    registerChangeInAuRestoProvinces() {
        this.eventSubscriber = this.eventManager.subscribe('auRestoProvinceListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
