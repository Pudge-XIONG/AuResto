/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuRestoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuRestoGenderAuRestoDetailComponent } from '../../../../../../main/webapp/app/entities/au-resto-gender/au-resto-gender-au-resto-detail.component';
import { AuRestoGenderAuRestoService } from '../../../../../../main/webapp/app/entities/au-resto-gender/au-resto-gender-au-resto.service';
import { AuRestoGenderAuResto } from '../../../../../../main/webapp/app/entities/au-resto-gender/au-resto-gender-au-resto.model';

describe('Component Tests', () => {

    describe('AuRestoGenderAuResto Management Detail Component', () => {
        let comp: AuRestoGenderAuRestoDetailComponent;
        let fixture: ComponentFixture<AuRestoGenderAuRestoDetailComponent>;
        let service: AuRestoGenderAuRestoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuRestoTestModule],
                declarations: [AuRestoGenderAuRestoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuRestoGenderAuRestoService,
                    JhiEventManager
                ]
            }).overrideTemplate(AuRestoGenderAuRestoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuRestoGenderAuRestoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuRestoGenderAuRestoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuRestoGenderAuResto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auRestoGender).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
