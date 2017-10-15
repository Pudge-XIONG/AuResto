import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { AuRestoReservationAuResto } from './au-resto-reservation-au-resto.model';
import { AuRestoReservationAuRestoService } from './au-resto-reservation-au-resto.service';

@Injectable()
export class AuRestoReservationAuRestoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private auRestoReservationService: AuRestoReservationAuRestoService

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
                this.auRestoReservationService.find(id).subscribe((auRestoReservation) => {
                    auRestoReservation.reserveDate = this.datePipe
                        .transform(auRestoReservation.reserveDate, 'yyyy-MM-ddTHH:mm:ss');
                    auRestoReservation.reserveForDate = this.datePipe
                        .transform(auRestoReservation.reserveForDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.auRestoReservationModalRef(component, auRestoReservation);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.auRestoReservationModalRef(component, new AuRestoReservationAuResto());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    auRestoReservationModalRef(component: Component, auRestoReservation: AuRestoReservationAuResto): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.auRestoReservation = auRestoReservation;
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
