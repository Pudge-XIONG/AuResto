import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AuRestoBillStatusAuResto } from './au-resto-bill-status-au-resto.model';
import { AuRestoBillStatusAuRestoService } from './au-resto-bill-status-au-resto.service';

@Injectable()
export class AuRestoBillStatusAuRestoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private auRestoBillStatusService: AuRestoBillStatusAuRestoService

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
                this.auRestoBillStatusService.find(id).subscribe((auRestoBillStatus) => {
                    this.ngbModalRef = this.auRestoBillStatusModalRef(component, auRestoBillStatus);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.auRestoBillStatusModalRef(component, new AuRestoBillStatusAuResto());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    auRestoBillStatusModalRef(component: Component, auRestoBillStatus: AuRestoBillStatusAuResto): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.auRestoBillStatus = auRestoBillStatus;
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
