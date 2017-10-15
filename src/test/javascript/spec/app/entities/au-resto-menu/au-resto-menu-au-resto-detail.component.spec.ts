/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuRestoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuRestoMenuAuRestoDetailComponent } from '../../../../../../main/webapp/app/entities/au-resto-menu/au-resto-menu-au-resto-detail.component';
import { AuRestoMenuAuRestoService } from '../../../../../../main/webapp/app/entities/au-resto-menu/au-resto-menu-au-resto.service';
import { AuRestoMenuAuResto } from '../../../../../../main/webapp/app/entities/au-resto-menu/au-resto-menu-au-resto.model';

describe('Component Tests', () => {

    describe('AuRestoMenuAuResto Management Detail Component', () => {
        let comp: AuRestoMenuAuRestoDetailComponent;
        let fixture: ComponentFixture<AuRestoMenuAuRestoDetailComponent>;
        let service: AuRestoMenuAuRestoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuRestoTestModule],
                declarations: [AuRestoMenuAuRestoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuRestoMenuAuRestoService,
                    JhiEventManager
                ]
            }).overrideTemplate(AuRestoMenuAuRestoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuRestoMenuAuRestoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuRestoMenuAuRestoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuRestoMenuAuResto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auRestoMenu).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
