/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuRestoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuRestoProfileAuRestoDetailComponent } from '../../../../../../main/webapp/app/entities/au-resto-profile/au-resto-profile-au-resto-detail.component';
import { AuRestoProfileAuRestoService } from '../../../../../../main/webapp/app/entities/au-resto-profile/au-resto-profile-au-resto.service';
import { AuRestoProfileAuResto } from '../../../../../../main/webapp/app/entities/au-resto-profile/au-resto-profile-au-resto.model';

describe('Component Tests', () => {

    describe('AuRestoProfileAuResto Management Detail Component', () => {
        let comp: AuRestoProfileAuRestoDetailComponent;
        let fixture: ComponentFixture<AuRestoProfileAuRestoDetailComponent>;
        let service: AuRestoProfileAuRestoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuRestoTestModule],
                declarations: [AuRestoProfileAuRestoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuRestoProfileAuRestoService,
                    JhiEventManager
                ]
            }).overrideTemplate(AuRestoProfileAuRestoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuRestoProfileAuRestoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuRestoProfileAuRestoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuRestoProfileAuResto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auRestoProfile).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
