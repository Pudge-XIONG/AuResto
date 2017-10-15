import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AuRestoRecipeAuResto } from './au-resto-recipe-au-resto.model';
import { AuRestoRecipeAuRestoService } from './au-resto-recipe-au-resto.service';

@Injectable()
export class AuRestoRecipeAuRestoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private auRestoRecipeService: AuRestoRecipeAuRestoService

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
                this.auRestoRecipeService.find(id).subscribe((auRestoRecipe) => {
                    this.ngbModalRef = this.auRestoRecipeModalRef(component, auRestoRecipe);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.auRestoRecipeModalRef(component, new AuRestoRecipeAuResto());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    auRestoRecipeModalRef(component: Component, auRestoRecipe: AuRestoRecipeAuResto): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.auRestoRecipe = auRestoRecipe;
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
