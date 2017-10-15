/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuRestoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuRestoFormulaTypeAuRestoDetailComponent } from '../../../../../../main/webapp/app/entities/au-resto-formula-type/au-resto-formula-type-au-resto-detail.component';
import { AuRestoFormulaTypeAuRestoService } from '../../../../../../main/webapp/app/entities/au-resto-formula-type/au-resto-formula-type-au-resto.service';
import { AuRestoFormulaTypeAuResto } from '../../../../../../main/webapp/app/entities/au-resto-formula-type/au-resto-formula-type-au-resto.model';

describe('Component Tests', () => {

    describe('AuRestoFormulaTypeAuResto Management Detail Component', () => {
        let comp: AuRestoFormulaTypeAuRestoDetailComponent;
        let fixture: ComponentFixture<AuRestoFormulaTypeAuRestoDetailComponent>;
        let service: AuRestoFormulaTypeAuRestoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuRestoTestModule],
                declarations: [AuRestoFormulaTypeAuRestoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuRestoFormulaTypeAuRestoService,
                    JhiEventManager
                ]
            }).overrideTemplate(AuRestoFormulaTypeAuRestoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuRestoFormulaTypeAuRestoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuRestoFormulaTypeAuRestoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuRestoFormulaTypeAuResto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auRestoFormulaType).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
