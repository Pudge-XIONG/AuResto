import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AuRestoRecipeTypeAuResto } from './au-resto-recipe-type-au-resto.model';
import { AuRestoRecipeTypeAuRestoService } from './au-resto-recipe-type-au-resto.service';

@Injectable()
export class AuRestoRecipeTypeAuRestoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private auRestoRecipeTypeService: AuRestoRecipeTypeAuRestoService

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
                this.auRestoRecipeTypeService.find(id).subscribe((auRestoRecipeType) => {
                    this.ngbModalRef = this.auRestoRecipeTypeModalRef(component, auRestoRecipeType);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.auRestoRecipeTypeModalRef(component, new AuRestoRecipeTypeAuResto());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    auRestoRecipeTypeModalRef(component: Component, auRestoRecipeType: AuRestoRecipeTypeAuResto): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.auRestoRecipeType = auRestoRecipeType;
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
