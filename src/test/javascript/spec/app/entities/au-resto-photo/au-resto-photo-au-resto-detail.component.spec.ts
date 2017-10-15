/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuRestoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuRestoPhotoAuRestoDetailComponent } from '../../../../../../main/webapp/app/entities/au-resto-photo/au-resto-photo-au-resto-detail.component';
import { AuRestoPhotoAuRestoService } from '../../../../../../main/webapp/app/entities/au-resto-photo/au-resto-photo-au-resto.service';
import { AuRestoPhotoAuResto } from '../../../../../../main/webapp/app/entities/au-resto-photo/au-resto-photo-au-resto.model';

describe('Component Tests', () => {

    describe('AuRestoPhotoAuResto Management Detail Component', () => {
        let comp: AuRestoPhotoAuRestoDetailComponent;
        let fixture: ComponentFixture<AuRestoPhotoAuRestoDetailComponent>;
        let service: AuRestoPhotoAuRestoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuRestoTestModule],
                declarations: [AuRestoPhotoAuRestoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuRestoPhotoAuRestoService,
                    JhiEventManager
                ]
            }).overrideTemplate(AuRestoPhotoAuRestoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuRestoPhotoAuRestoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuRestoPhotoAuRestoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuRestoPhotoAuResto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auRestoPhoto).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
