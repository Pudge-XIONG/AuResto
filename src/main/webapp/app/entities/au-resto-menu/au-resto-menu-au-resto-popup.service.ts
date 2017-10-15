import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { AuRestoMenuAuResto } from './au-resto-menu-au-resto.model';
import { AuRestoMenuAuRestoService } from './au-resto-menu-au-resto.service';

@Injectable()
export class AuRestoMenuAuRestoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private auRestoMenuService: AuRestoMenuAuRestoService

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
                this.auRestoMenuService.find(id).subscribe((auRestoMenu) => {
                    auRestoMenu.date = this.datePipe
                        .transform(auRestoMenu.date, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.auRestoMenuModalRef(component, auRestoMenu);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.auRestoMenuModalRef(component, new AuRestoMenuAuResto());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    auRestoMenuModalRef(component: Component, auRestoMenu: AuRestoMenuAuResto): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.auRestoMenu = auRestoMenu;
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
