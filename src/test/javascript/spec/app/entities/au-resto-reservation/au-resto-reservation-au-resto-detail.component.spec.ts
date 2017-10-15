/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuRestoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuRestoReservationAuRestoDetailComponent } from '../../../../../../main/webapp/app/entities/au-resto-reservation/au-resto-reservation-au-resto-detail.component';
import { AuRestoReservationAuRestoService } from '../../../../../../main/webapp/app/entities/au-resto-reservation/au-resto-reservation-au-resto.service';
import { AuRestoReservationAuResto } from '../../../../../../main/webapp/app/entities/au-resto-reservation/au-resto-reservation-au-resto.model';

describe('Component Tests', () => {

    describe('AuRestoReservationAuResto Management Detail Component', () => {
        let comp: AuRestoReservationAuRestoDetailComponent;
        let fixture: ComponentFixture<AuRestoReservationAuRestoDetailComponent>;
        let service: AuRestoReservationAuRestoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuRestoTestModule],
                declarations: [AuRestoReservationAuRestoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuRestoReservationAuRestoService,
                    JhiEventManager
                ]
            }).overrideTemplate(AuRestoReservationAuRestoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuRestoReservationAuRestoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuRestoReservationAuRestoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuRestoReservationAuResto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auRestoReservation).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
