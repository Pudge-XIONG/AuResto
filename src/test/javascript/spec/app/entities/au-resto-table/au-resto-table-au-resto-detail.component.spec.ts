/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuRestoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuRestoTableAuRestoDetailComponent } from '../../../../../../main/webapp/app/entities/au-resto-table/au-resto-table-au-resto-detail.component';
import { AuRestoTableAuRestoService } from '../../../../../../main/webapp/app/entities/au-resto-table/au-resto-table-au-resto.service';
import { AuRestoTableAuResto } from '../../../../../../main/webapp/app/entities/au-resto-table/au-resto-table-au-resto.model';

describe('Component Tests', () => {

    describe('AuRestoTableAuResto Management Detail Component', () => {
        let comp: AuRestoTableAuRestoDetailComponent;
        let fixture: ComponentFixture<AuRestoTableAuRestoDetailComponent>;
        let service: AuRestoTableAuRestoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuRestoTestModule],
                declarations: [AuRestoTableAuRestoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuRestoTableAuRestoService,
                    JhiEventManager
                ]
            }).overrideTemplate(AuRestoTableAuRestoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuRestoTableAuRestoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuRestoTableAuRestoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuRestoTableAuResto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auRestoTable).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
