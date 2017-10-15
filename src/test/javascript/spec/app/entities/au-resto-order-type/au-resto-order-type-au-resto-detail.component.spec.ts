/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuRestoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuRestoOrderTypeAuRestoDetailComponent } from '../../../../../../main/webapp/app/entities/au-resto-order-type/au-resto-order-type-au-resto-detail.component';
import { AuRestoOrderTypeAuRestoService } from '../../../../../../main/webapp/app/entities/au-resto-order-type/au-resto-order-type-au-resto.service';
import { AuRestoOrderTypeAuResto } from '../../../../../../main/webapp/app/entities/au-resto-order-type/au-resto-order-type-au-resto.model';

describe('Component Tests', () => {

    describe('AuRestoOrderTypeAuResto Management Detail Component', () => {
        let comp: AuRestoOrderTypeAuRestoDetailComponent;
        let fixture: ComponentFixture<AuRestoOrderTypeAuRestoDetailComponent>;
        let service: AuRestoOrderTypeAuRestoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuRestoTestModule],
                declarations: [AuRestoOrderTypeAuRestoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuRestoOrderTypeAuRestoService,
                    JhiEventManager
                ]
            }).overrideTemplate(AuRestoOrderTypeAuRestoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuRestoOrderTypeAuRestoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuRestoOrderTypeAuRestoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuRestoOrderTypeAuResto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auRestoOrderType).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
