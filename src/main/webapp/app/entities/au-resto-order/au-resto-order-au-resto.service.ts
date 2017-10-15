import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { AuRestoOrderAuResto } from './au-resto-order-au-resto.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AuRestoOrderAuRestoService {

    private resourceUrl = SERVER_API_URL + 'api/au-resto-orders';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/au-resto-orders';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(auRestoOrder: AuRestoOrderAuResto): Observable<AuRestoOrderAuResto> {
        const copy = this.convert(auRestoOrder);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(auRestoOrder: AuRestoOrderAuResto): Observable<AuRestoOrderAuResto> {
        const copy = this.convert(auRestoOrder);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<AuRestoOrderAuResto> {
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
     * Convert a returned JSON object to AuRestoOrderAuResto.
     */
    private convertItemFromServer(json: any): AuRestoOrderAuResto {
        const entity: AuRestoOrderAuResto = Object.assign(new AuRestoOrderAuResto(), json);
        entity.date = this.dateUtils
            .convertDateTimeFromServer(json.date);
        return entity;
    }

    /**
     * Convert a AuRestoOrderAuResto to a JSON which can be sent to the server.
     */
    private convert(auRestoOrder: AuRestoOrderAuResto): AuRestoOrderAuResto {
        const copy: AuRestoOrderAuResto = Object.assign({}, auRestoOrder);

        copy.date = this.dateUtils.toDate(auRestoOrder.date);
        return copy;
    }
}
