import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AuRestoFormulaTypeAuResto } from './au-resto-formula-type-au-resto.model';
import { AuRestoFormulaTypeAuRestoService } from './au-resto-formula-type-au-resto.service';

@Injectable()
export class AuRestoFormulaTypeAuRestoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private auRestoFormulaTypeService: AuRestoFormulaTypeAuRestoService

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
                this.auRestoFormulaTypeService.find(id).subscribe((auRestoFormulaType) => {
                    this.ngbModalRef = this.auRestoFormulaTypeModalRef(component, auRestoFormulaType);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.auRestoFormulaTypeModalRef(component, new AuRestoFormulaTypeAuResto());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    auRestoFormulaTypeModalRef(component: Component, auRestoFormulaType: AuRestoFormulaTypeAuResto): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.auRestoFormulaType = auRestoFormulaType;
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
