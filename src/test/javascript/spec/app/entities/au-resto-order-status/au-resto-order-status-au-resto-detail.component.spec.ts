/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuRestoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuRestoOrderStatusAuRestoDetailComponent } from '../../../../../../main/webapp/app/entities/au-resto-order-status/au-resto-order-status-au-resto-detail.component';
import { AuRestoOrderStatusAuRestoService } from '../../../../../../main/webapp/app/entities/au-resto-order-status/au-resto-order-status-au-resto.service';
import { AuRestoOrderStatusAuResto } from '../../../../../../main/webapp/app/entities/au-resto-order-status/au-resto-order-status-au-resto.model';

describe('Component Tests', () => {

    describe('AuRestoOrderStatusAuResto Management Detail Component', () => {
        let comp: AuRestoOrderStatusAuRestoDetailComponent;
        let fixture: ComponentFixture<AuRestoOrderStatusAuRestoDetailComponent>;
        let service: AuRestoOrderStatusAuRestoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuRestoTestModule],
                declarations: [AuRestoOrderStatusAuRestoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuRestoOrderStatusAuRestoService,
                    JhiEventManager
                ]
            }).overrideTemplate(AuRestoOrderStatusAuRestoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuRestoOrderStatusAuRestoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuRestoOrderStatusAuRestoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuRestoOrderStatusAuResto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auRestoOrderStatus).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
