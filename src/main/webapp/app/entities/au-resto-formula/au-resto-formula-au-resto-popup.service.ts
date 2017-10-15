import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AuRestoFormulaAuResto } from './au-resto-formula-au-resto.model';
import { AuRestoFormulaAuRestoService } from './au-resto-formula-au-resto.service';

@Injectable()
export class AuRestoFormulaAuRestoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private auRestoFormulaService: AuRestoFormulaAuRestoService

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
                this.auRestoFormulaService.find(id).subscribe((auRestoFormula) => {
                    this.ngbModalRef = this.auRestoFormulaModalRef(component, auRestoFormula);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.auRestoFormulaModalRef(component, new AuRestoFormulaAuResto());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    auRestoFormulaModalRef(component: Component, auRestoFormula: AuRestoFormulaAuResto): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.auRestoFormula = auRestoFormula;
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
