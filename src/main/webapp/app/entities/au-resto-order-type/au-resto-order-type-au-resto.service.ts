import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { AuRestoOrderTypeAuResto } from './au-resto-order-type-au-resto.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AuRestoOrderTypeAuRestoService {

    private resourceUrl = SERVER_API_URL + 'api/au-resto-order-types';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/au-resto-order-types';

    constructor(private http: Http) { }

    create(auRestoOrderType: AuRestoOrderTypeAuResto): Observable<AuRestoOrderTypeAuResto> {
        const copy = this.convert(auRestoOrderType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(auRestoOrderType: AuRestoOrderTypeAuResto): Observable<AuRestoOrderTypeAuResto> {
        const copy = this.convert(auRestoOrderType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<AuRestoOrderTypeAuResto> {
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
     * Convert a returned JSON object to AuRestoOrderTypeAuResto.
     */
    private convertItemFromServer(json: any): AuRestoOrderTypeAuResto {
        const entity: AuRestoOrderTypeAuResto = Object.assign(new AuRestoOrderTypeAuResto(), json);
        return entity;
    }

    /**
     * Convert a AuRestoOrderTypeAuResto to a JSON which can be sent to the server.
     */
    private convert(auRestoOrderType: AuRestoOrderTypeAuResto): AuRestoOrderTypeAuResto {
        const copy: AuRestoOrderTypeAuResto = Object.assign({}, auRestoOrderType);
        return copy;
    }
}
