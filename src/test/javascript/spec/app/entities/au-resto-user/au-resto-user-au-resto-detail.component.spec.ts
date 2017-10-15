/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuRestoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuRestoUserAuRestoDetailComponent } from '../../../../../../main/webapp/app/entities/au-resto-user/au-resto-user-au-resto-detail.component';
import { AuRestoUserAuRestoService } from '../../../../../../main/webapp/app/entities/au-resto-user/au-resto-user-au-resto.service';
import { AuRestoUserAuResto } from '../../../../../../main/webapp/app/entities/au-resto-user/au-resto-user-au-resto.model';

describe('Component Tests', () => {

    describe('AuRestoUserAuResto Management Detail Component', () => {
        let comp: AuRestoUserAuRestoDetailComponent;
        let fixture: ComponentFixture<AuRestoUserAuRestoDetailComponent>;
        let service: AuRestoUserAuRestoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuRestoTestModule],
                declarations: [AuRestoUserAuRestoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuRestoUserAuRestoService,
                    JhiEventManager
                ]
            }).overrideTemplate(AuRestoUserAuRestoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuRestoUserAuRestoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuRestoUserAuRestoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuRestoUserAuResto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auRestoUser).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
