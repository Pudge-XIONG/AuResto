/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuRestoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuRestoRestaurantAuRestoDetailComponent } from '../../../../../../main/webapp/app/entities/au-resto-restaurant/au-resto-restaurant-au-resto-detail.component';
import { AuRestoRestaurantAuRestoService } from '../../../../../../main/webapp/app/entities/au-resto-restaurant/au-resto-restaurant-au-resto.service';
import { AuRestoRestaurantAuResto } from '../../../../../../main/webapp/app/entities/au-resto-restaurant/au-resto-restaurant-au-resto.model';

describe('Component Tests', () => {

    describe('AuRestoRestaurantAuResto Management Detail Component', () => {
        let comp: AuRestoRestaurantAuRestoDetailComponent;
        let fixture: ComponentFixture<AuRestoRestaurantAuRestoDetailComponent>;
        let service: AuRestoRestaurantAuRestoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuRestoTestModule],
                declarations: [AuRestoRestaurantAuRestoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuRestoRestaurantAuRestoService,
                    JhiEventManager
                ]
            }).overrideTemplate(AuRestoRestaurantAuRestoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuRestoRestaurantAuRestoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuRestoRestaurantAuRestoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuRestoRestaurantAuResto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auRestoRestaurant).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
