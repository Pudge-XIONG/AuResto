/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuRestoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuRestoFormulaAuRestoDetailComponent } from '../../../../../../main/webapp/app/entities/au-resto-formula/au-resto-formula-au-resto-detail.component';
import { AuRestoFormulaAuRestoService } from '../../../../../../main/webapp/app/entities/au-resto-formula/au-resto-formula-au-resto.service';
import { AuRestoFormulaAuResto } from '../../../../../../main/webapp/app/entities/au-resto-formula/au-resto-formula-au-resto.model';

describe('Component Tests', () => {

    describe('AuRestoFormulaAuResto Management Detail Component', () => {
        let comp: AuRestoFormulaAuRestoDetailComponent;
        let fixture: ComponentFixture<AuRestoFormulaAuRestoDetailComponent>;
        let service: AuRestoFormulaAuRestoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuRestoTestModule],
                declarations: [AuRestoFormulaAuRestoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuRestoFormulaAuRestoService,
                    JhiEventManager
                ]
            }).overrideTemplate(AuRestoFormulaAuRestoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuRestoFormulaAuRestoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuRestoFormulaAuRestoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuRestoFormulaAuResto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auRestoFormula).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
