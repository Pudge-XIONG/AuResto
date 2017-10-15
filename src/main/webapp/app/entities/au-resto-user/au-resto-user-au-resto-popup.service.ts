import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AuRestoUserAuResto } from './au-resto-user-au-resto.model';
import { AuRestoUserAuRestoService } from './au-resto-user-au-resto.service';

@Injectable()
export class AuRestoUserAuRestoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private auRestoUserService: AuRestoUserAuRestoService

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
                this.auRestoUserService.find(id).subscribe((auRestoUser) => {
                    this.ngbModalRef = this.auRestoUserModalRef(component, auRestoUser);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.auRestoUserModalRef(component, new AuRestoUserAuResto());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    auRestoUserModalRef(component: Component, auRestoUser: AuRestoUserAuResto): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.auRestoUser = auRestoUser;
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
