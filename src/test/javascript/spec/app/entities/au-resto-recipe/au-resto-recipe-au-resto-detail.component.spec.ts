/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuRestoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuRestoRecipeAuRestoDetailComponent } from '../../../../../../main/webapp/app/entities/au-resto-recipe/au-resto-recipe-au-resto-detail.component';
import { AuRestoRecipeAuRestoService } from '../../../../../../main/webapp/app/entities/au-resto-recipe/au-resto-recipe-au-resto.service';
import { AuRestoRecipeAuResto } from '../../../../../../main/webapp/app/entities/au-resto-recipe/au-resto-recipe-au-resto.model';

describe('Component Tests', () => {

    describe('AuRestoRecipeAuResto Management Detail Component', () => {
        let comp: AuRestoRecipeAuRestoDetailComponent;
        let fixture: ComponentFixture<AuRestoRecipeAuRestoDetailComponent>;
        let service: AuRestoRecipeAuRestoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuRestoTestModule],
                declarations: [AuRestoRecipeAuRestoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuRestoRecipeAuRestoService,
                    JhiEventManager
                ]
            }).overrideTemplate(AuRestoRecipeAuRestoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuRestoRecipeAuRestoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuRestoRecipeAuRestoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuRestoRecipeAuResto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auRestoRecipe).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
