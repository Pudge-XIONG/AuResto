/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuRestoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuRestoBillAuRestoDetailComponent } from '../../../../../../main/webapp/app/entities/au-resto-bill/au-resto-bill-au-resto-detail.component';
import { AuRestoBillAuRestoService } from '../../../../../../main/webapp/app/entities/au-resto-bill/au-resto-bill-au-resto.service';
import { AuRestoBillAuResto } from '../../../../../../main/webapp/app/entities/au-resto-bill/au-resto-bill-au-resto.model';

describe('Component Tests', () => {

    describe('AuRestoBillAuResto Management Detail Component', () => {
        let comp: AuRestoBillAuRestoDetailComponent;
        let fixture: ComponentFixture<AuRestoBillAuRestoDetailComponent>;
        let service: AuRestoBillAuRestoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuRestoTestModule],
                declarations: [AuRestoBillAuRestoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuRestoBillAuRestoService,
                    JhiEventManager
                ]
            }).overrideTemplate(AuRestoBillAuRestoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuRestoBillAuRestoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuRestoBillAuRestoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuRestoBillAuResto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auRestoBill).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
