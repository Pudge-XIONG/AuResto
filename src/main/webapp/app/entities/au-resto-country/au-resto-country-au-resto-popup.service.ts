import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AuRestoCountryAuResto } from './au-resto-country-au-resto.model';
import { AuRestoCountryAuRestoService } from './au-resto-country-au-resto.service';

@Injectable()
export class AuRestoCountryAuRestoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private auRestoCountryService: AuRestoCountryAuRestoService

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
                this.auRestoCountryService.find(id).subscribe((auRestoCountry) => {
                    this.ngbModalRef = this.auRestoCountryModalRef(component, auRestoCountry);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.auRestoCountryModalRef(component, new AuRestoCountryAuResto());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    auRestoCountryModalRef(component: Component, auRestoCountry: AuRestoCountryAuResto): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.auRestoCountry = auRestoCountry;
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
