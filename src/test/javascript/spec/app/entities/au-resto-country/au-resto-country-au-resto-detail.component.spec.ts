/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuRestoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuRestoCountryAuRestoDetailComponent } from '../../../../../../main/webapp/app/entities/au-resto-country/au-resto-country-au-resto-detail.component';
import { AuRestoCountryAuRestoService } from '../../../../../../main/webapp/app/entities/au-resto-country/au-resto-country-au-resto.service';
import { AuRestoCountryAuResto } from '../../../../../../main/webapp/app/entities/au-resto-country/au-resto-country-au-resto.model';

describe('Component Tests', () => {

    describe('AuRestoCountryAuResto Management Detail Component', () => {
        let comp: AuRestoCountryAuRestoDetailComponent;
        let fixture: ComponentFixture<AuRestoCountryAuRestoDetailComponent>;
        let service: AuRestoCountryAuRestoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuRestoTestModule],
                declarations: [AuRestoCountryAuRestoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuRestoCountryAuRestoService,
                    JhiEventManager
                ]
            }).overrideTemplate(AuRestoCountryAuRestoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuRestoCountryAuRestoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuRestoCountryAuRestoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuRestoCountryAuResto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auRestoCountry).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
