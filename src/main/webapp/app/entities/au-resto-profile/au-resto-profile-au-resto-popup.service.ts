import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AuRestoProfileAuResto } from './au-resto-profile-au-resto.model';
import { AuRestoProfileAuRestoService } from './au-resto-profile-au-resto.service';

@Injectable()
export class AuRestoProfileAuRestoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private auRestoProfileService: AuRestoProfileAuRestoService

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
                this.auRestoProfileService.find(id).subscribe((auRestoProfile) => {
                    this.ngbModalRef = this.auRestoProfileModalRef(component, auRestoProfile);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.auRestoProfileModalRef(component, new AuRestoProfileAuResto());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    auRestoProfileModalRef(component: Component, auRestoProfile: AuRestoProfileAuResto): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.auRestoProfile = auRestoProfile;
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
