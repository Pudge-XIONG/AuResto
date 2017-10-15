/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuRestoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuRestoBillStatusAuRestoDetailComponent } from '../../../../../../main/webapp/app/entities/au-resto-bill-status/au-resto-bill-status-au-resto-detail.component';
import { AuRestoBillStatusAuRestoService } from '../../../../../../main/webapp/app/entities/au-resto-bill-status/au-resto-bill-status-au-resto.service';
import { AuRestoBillStatusAuResto } from '../../../../../../main/webapp/app/entities/au-resto-bill-status/au-resto-bill-status-au-resto.model';

describe('Component Tests', () => {

    describe('AuRestoBillStatusAuResto Management Detail Component', () => {
        let comp: AuRestoBillStatusAuRestoDetailComponent;
        let fixture: ComponentFixture<AuRestoBillStatusAuRestoDetailComponent>;
        let service: AuRestoBillStatusAuRestoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuRestoTestModule],
                declarations: [AuRestoBillStatusAuRestoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuRestoBillStatusAuRestoService,
                    JhiEventManager
                ]
            }).overrideTemplate(AuRestoBillStatusAuRestoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuRestoBillStatusAuRestoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuRestoBillStatusAuRestoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuRestoBillStatusAuResto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auRestoBillStatus).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
