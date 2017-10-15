import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { AuRestoBillStatusAuResto } from './au-resto-bill-status-au-resto.model';
import { AuRestoBillStatusAuRestoService } from './au-resto-bill-status-au-resto.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-au-resto-bill-status-au-resto',
    templateUrl: './au-resto-bill-status-au-resto.component.html'
})
export class AuRestoBillStatusAuRestoComponent implements OnInit, OnDestroy {
auRestoBillStatuses: AuRestoBillStatusAuResto[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private auRestoBillStatusService: AuRestoBillStatusAuRestoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.auRestoBillStatusService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.auRestoBillStatuses = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.auRestoBillStatusService.query().subscribe(
            (res: ResponseWrapper) => {
                this.auRestoBillStatuses = res.json;
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
        this.registerChangeInAuRestoBillStatuses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AuRestoBillStatusAuResto) {
        return item.id;
    }
    registerChangeInAuRestoBillStatuses() {
        this.eventSubscriber = this.eventManager.subscribe('auRestoBillStatusListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
