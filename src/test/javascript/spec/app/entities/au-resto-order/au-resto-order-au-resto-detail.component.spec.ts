/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuRestoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuRestoOrderAuRestoDetailComponent } from '../../../../../../main/webapp/app/entities/au-resto-order/au-resto-order-au-resto-detail.component';
import { AuRestoOrderAuRestoService } from '../../../../../../main/webapp/app/entities/au-resto-order/au-resto-order-au-resto.service';
import { AuRestoOrderAuResto } from '../../../../../../main/webapp/app/entities/au-resto-order/au-resto-order-au-resto.model';

describe('Component Tests', () => {

    describe('AuRestoOrderAuResto Management Detail Component', () => {
        let comp: AuRestoOrderAuRestoDetailComponent;
        let fixture: ComponentFixture<AuRestoOrderAuRestoDetailComponent>;
        let service: AuRestoOrderAuRestoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuRestoTestModule],
                declarations: [AuRestoOrderAuRestoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuRestoOrderAuRestoService,
                    JhiEventManager
                ]
            }).overrideTemplate(AuRestoOrderAuRestoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuRestoOrderAuRestoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuRestoOrderAuRestoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuRestoOrderAuResto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auRestoOrder).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
