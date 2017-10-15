/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuRestoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuRestoCityAuRestoDetailComponent } from '../../../../../../main/webapp/app/entities/au-resto-city/au-resto-city-au-resto-detail.component';
import { AuRestoCityAuRestoService } from '../../../../../../main/webapp/app/entities/au-resto-city/au-resto-city-au-resto.service';
import { AuRestoCityAuResto } from '../../../../../../main/webapp/app/entities/au-resto-city/au-resto-city-au-resto.model';

describe('Component Tests', () => {

    describe('AuRestoCityAuResto Management Detail Component', () => {
        let comp: AuRestoCityAuRestoDetailComponent;
        let fixture: ComponentFixture<AuRestoCityAuRestoDetailComponent>;
        let service: AuRestoCityAuRestoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuRestoTestModule],
                declarations: [AuRestoCityAuRestoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuRestoCityAuRestoService,
                    JhiEventManager
                ]
            }).overrideTemplate(AuRestoCityAuRestoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuRestoCityAuRestoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuRestoCityAuRestoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuRestoCityAuResto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auRestoCity).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
