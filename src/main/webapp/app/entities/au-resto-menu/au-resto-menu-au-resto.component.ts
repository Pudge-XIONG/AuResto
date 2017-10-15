import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { AuRestoMenuAuResto } from './au-resto-menu-au-resto.model';
import { AuRestoMenuAuRestoService } from './au-resto-menu-au-resto.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-au-resto-menu-au-resto',
    templateUrl: './au-resto-menu-au-resto.component.html'
})
export class AuRestoMenuAuRestoComponent implements OnInit, OnDestroy {
auRestoMenus: AuRestoMenuAuResto[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private auRestoMenuService: AuRestoMenuAuRestoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.auRestoMenuService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.auRestoMenus = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.auRestoMenuService.query().subscribe(
            (res: ResponseWrapper) => {
                this.auRestoMenus = res.json;
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
        this.registerChangeInAuRestoMenus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AuRestoMenuAuResto) {
        return item.id;
    }
    registerChangeInAuRestoMenus() {
        this.eventSubscriber = this.eventManager.subscribe('auRestoMenuListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
