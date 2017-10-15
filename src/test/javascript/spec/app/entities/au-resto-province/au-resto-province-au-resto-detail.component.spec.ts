/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuRestoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuRestoProvinceAuRestoDetailComponent } from '../../../../../../main/webapp/app/entities/au-resto-province/au-resto-province-au-resto-detail.component';
import { AuRestoProvinceAuRestoService } from '../../../../../../main/webapp/app/entities/au-resto-province/au-resto-province-au-resto.service';
import { AuRestoProvinceAuResto } from '../../../../../../main/webapp/app/entities/au-resto-province/au-resto-province-au-resto.model';

describe('Component Tests', () => {

    describe('AuRestoProvinceAuResto Management Detail Component', () => {
        let comp: AuRestoProvinceAuRestoDetailComponent;
        let fixture: ComponentFixture<AuRestoProvinceAuRestoDetailComponent>;
        let service: AuRestoProvinceAuRestoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuRestoTestModule],
                declarations: [AuRestoProvinceAuRestoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuRestoProvinceAuRestoService,
                    JhiEventManager
                ]
            }).overrideTemplate(AuRestoProvinceAuRestoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuRestoProvinceAuRestoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuRestoProvinceAuRestoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuRestoProvinceAuResto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auRestoProvince).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
