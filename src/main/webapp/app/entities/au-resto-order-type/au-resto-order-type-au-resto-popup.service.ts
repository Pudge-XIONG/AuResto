import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AuRestoOrderTypeAuResto } from './au-resto-order-type-au-resto.model';
import { AuRestoOrderTypeAuRestoService } from './au-resto-order-type-au-resto.service';

@Injectable()
export class AuRestoOrderTypeAuRestoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private auRestoOrderTypeService: AuRestoOrderTypeAuRestoService

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
                this.auRestoOrderTypeService.find(id).subscribe((auRestoOrderType) => {
                    this.ngbModalRef = this.auRestoOrderTypeModalRef(component, auRestoOrderType);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.auRestoOrderTypeModalRef(component, new AuRestoOrderTypeAuResto());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    auRestoOrderTypeModalRef(component: Component, auRestoOrderType: AuRestoOrderTypeAuResto): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.auRestoOrderType = auRestoOrderType;
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
