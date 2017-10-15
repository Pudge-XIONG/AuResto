/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuRestoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuRestoRestaurantTypeAuRestoDetailComponent } from '../../../../../../main/webapp/app/entities/au-resto-restaurant-type/au-resto-restaurant-type-au-resto-detail.component';
import { AuRestoRestaurantTypeAuRestoService } from '../../../../../../main/webapp/app/entities/au-resto-restaurant-type/au-resto-restaurant-type-au-resto.service';
import { AuRestoRestaurantTypeAuResto } from '../../../../../../main/webapp/app/entities/au-resto-restaurant-type/au-resto-restaurant-type-au-resto.model';

describe('Component Tests', () => {

    describe('AuRestoRestaurantTypeAuResto Management Detail Component', () => {
        let comp: AuRestoRestaurantTypeAuRestoDetailComponent;
        let fixture: ComponentFixture<AuRestoRestaurantTypeAuRestoDetailComponent>;
        let service: AuRestoRestaurantTypeAuRestoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuRestoTestModule],
                declarations: [AuRestoRestaurantTypeAuRestoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuRestoRestaurantTypeAuRestoService,
                    JhiEventManager
                ]
            }).overrideTemplate(AuRestoRestaurantTypeAuRestoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuRestoRestaurantTypeAuRestoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuRestoRestaurantTypeAuRestoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuRestoRestaurantTypeAuResto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auRestoRestaurantType).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
