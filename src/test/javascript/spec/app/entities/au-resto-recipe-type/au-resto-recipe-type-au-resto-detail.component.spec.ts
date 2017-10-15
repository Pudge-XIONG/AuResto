/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuRestoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuRestoRecipeTypeAuRestoDetailComponent } from '../../../../../../main/webapp/app/entities/au-resto-recipe-type/au-resto-recipe-type-au-resto-detail.component';
import { AuRestoRecipeTypeAuRestoService } from '../../../../../../main/webapp/app/entities/au-resto-recipe-type/au-resto-recipe-type-au-resto.service';
import { AuRestoRecipeTypeAuResto } from '../../../../../../main/webapp/app/entities/au-resto-recipe-type/au-resto-recipe-type-au-resto.model';

describe('Component Tests', () => {

    describe('AuRestoRecipeTypeAuResto Management Detail Component', () => {
        let comp: AuRestoRecipeTypeAuRestoDetailComponent;
        let fixture: ComponentFixture<AuRestoRecipeTypeAuRestoDetailComponent>;
        let service: AuRestoRecipeTypeAuRestoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuRestoTestModule],
                declarations: [AuRestoRecipeTypeAuRestoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuRestoRecipeTypeAuRestoService,
                    JhiEventManager
                ]
            }).overrideTemplate(AuRestoRecipeTypeAuRestoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuRestoRecipeTypeAuRestoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuRestoRecipeTypeAuRestoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuRestoRecipeTypeAuResto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auRestoRecipeType).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
