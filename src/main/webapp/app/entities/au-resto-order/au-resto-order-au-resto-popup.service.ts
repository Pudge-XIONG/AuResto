import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { AuRestoOrderAuResto } from './au-resto-order-au-resto.model';
import { AuRestoOrderAuRestoService } from './au-resto-order-au-resto.service';

@Injectable()
export class AuRestoOrderAuRestoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private auRestoOrderService: AuRestoOrderAuRestoService

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
                this.auRestoOrderService.find(id).subscribe((auRestoOrder) => {
                    auRestoOrder.date = this.datePipe
                        .transform(auRestoOrder.date, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.auRestoOrderModalRef(component, auRestoOrder);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.auRestoOrderModalRef(component, new AuRestoOrderAuResto());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    auRestoOrderModalRef(component: Component, auRestoOrder: AuRestoOrderAuResto): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.auRestoOrder = auRestoOrder;
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
