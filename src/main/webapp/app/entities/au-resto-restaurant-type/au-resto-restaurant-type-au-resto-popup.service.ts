import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AuRestoRestaurantTypeAuResto } from './au-resto-restaurant-type-au-resto.model';
import { AuRestoRestaurantTypeAuRestoService } from './au-resto-restaurant-type-au-resto.service';

@Injectable()
export class AuRestoRestaurantTypeAuRestoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private auRestoRestaurantTypeService: AuRestoRestaurantTypeAuRestoService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.auRestoRestaurantTypeService.find(id).subscribe((auRestoRestaurantType) => {
                    this.ngbModalRef = this.auRestoRestaurantTypeModalRef(component, auRestoRestaurantType);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.auRestoRestaurantTypeModalRef(component, new AuRestoRestaurantTypeAuResto());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    auRestoRestaurantTypeModalRef(component: Component, auRestoRestaurantType: AuRestoRestaurantTypeAuResto): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.auRestoRestaurantType = auRestoRestaurantType;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
