import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AuRestoOrderStatusAuResto } from './au-resto-order-status-au-resto.model';
import { AuRestoOrderStatusAuRestoService } from './au-resto-order-status-au-resto.service';

@Injectable()
export class AuRestoOrderStatusAuRestoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private auRestoOrderStatusService: AuRestoOrderStatusAuRestoService

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
                this.auRestoOrderStatusService.find(id).subscribe((auRestoOrderStatus) => {
                    this.ngbModalRef = this.auRestoOrderStatusModalRef(component, auRestoOrderStatus);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.auRestoOrderStatusModalRef(component, new AuRestoOrderStatusAuResto());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    auRestoOrderStatusModalRef(component: Component, auRestoOrderStatus: AuRestoOrderStatusAuResto): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.auRestoOrderStatus = auRestoOrderStatus;
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
