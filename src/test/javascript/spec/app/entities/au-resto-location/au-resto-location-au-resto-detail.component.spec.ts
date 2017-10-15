/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuRestoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuRestoLocationAuRestoDetailComponent } from '../../../../../../main/webapp/app/entities/au-resto-location/au-resto-location-au-resto-detail.component';
import { AuRestoLocationAuRestoService } from '../../../../../../main/webapp/app/entities/au-resto-location/au-resto-location-au-resto.service';
import { AuRestoLocationAuResto } from '../../../../../../main/webapp/app/entities/au-resto-location/au-resto-location-au-resto.model';

describe('Component Tests', () => {

    describe('AuRestoLocationAuResto Management Detail Component', () => {
        let comp: AuRestoLocationAuRestoDetailComponent;
        let fixture: ComponentFixture<AuRestoLocationAuRestoDetailComponent>;
        let service: AuRestoLocationAuRestoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuRestoTestModule],
                declarations: [AuRestoLocationAuRestoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuRestoLocationAuRestoService,
                    JhiEventManager
                ]
            }).overrideTemplate(AuRestoLocationAuRestoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuRestoLocationAuRestoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuRestoLocationAuRestoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuRestoLocationAuResto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auRestoLocation).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
