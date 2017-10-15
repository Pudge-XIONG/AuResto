import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AuRestoProvinceAuResto } from './au-resto-province-au-resto.model';
import { AuRestoProvinceAuRestoService } from './au-resto-province-au-resto.service';

@Injectable()
export class AuRestoProvinceAuRestoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private auRestoProvinceService: AuRestoProvinceAuRestoService

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
                this.auRestoProvinceService.find(id).subscribe((auRestoProvince) => {
                    this.ngbModalRef = this.auRestoProvinceModalRef(component, auRestoProvince);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.auRestoProvinceModalRef(component, new AuRestoProvinceAuResto());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    auRestoProvinceModalRef(component: Component, auRestoProvince: AuRestoProvinceAuResto): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.auRestoProvince = auRestoProvince;
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
