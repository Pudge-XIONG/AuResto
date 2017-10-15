import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { AuRestoMenuAuResto } from './au-resto-menu-au-resto.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AuRestoMenuAuRestoService {

    private resourceUrl = SERVER_API_URL + 'api/au-resto-menus';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/au-resto-menus';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(auRestoMenu: AuRestoMenuAuResto): Observable<AuRestoMenuAuResto> {
        const copy = this.convert(auRestoMenu);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(auRestoMenu: AuRestoMenuAuResto): Observable<AuRestoMenuAuResto> {
        const copy = this.convert(auRestoMenu);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<AuRestoMenuAuResto> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to AuRestoMenuAuResto.
     */
    private convertItemFromServer(json: any): AuRestoMenuAuResto {
        const entity: AuRestoMenuAuResto = Object.assign(new AuRestoMenuAuResto(), json);
        entity.date = this.dateUtils
            .convertDateTimeFromServer(json.date);
        return entity;
    }

    /**
     * Convert a AuRestoMenuAuResto to a JSON which can be sent to the server.
     */
    private convert(auRestoMenu: AuRestoMenuAuResto): AuRestoMenuAuResto {
        const copy: AuRestoMenuAuResto = Object.assign({}, auRestoMenu);

        copy.date = this.dateUtils.toDate(auRestoMenu.date);
        return copy;
    }
}
