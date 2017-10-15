import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { AuRestoPhotoAuResto } from './au-resto-photo-au-resto.model';
import { AuRestoPhotoAuRestoService } from './au-resto-photo-au-resto.service';

@Component({
    selector: 'jhi-au-resto-photo-au-resto-detail',
    templateUrl: './au-resto-photo-au-resto-detail.component.html'
})
export class AuRestoPhotoAuRestoDetailComponent implements OnInit, OnDestroy {

    auRestoPhoto: AuRestoPhotoAuResto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private auRestoPhotoService: AuRestoPhotoAuRestoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuRestoPhotos();
    }

    load(id) {
        this.auRestoPhotoService.find(id).subscribe((auRestoPhoto) => {
            this.auRestoPhoto = auRestoPhoto;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuRestoPhotos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'auRestoPhotoListModification',
            (response) => this.load(this.auRestoPhoto.id)
        );
    }
}
