import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { AuRestoOrderStatusAuResto } from './au-resto-order-status-au-resto.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AuRestoOrderStatusAuRestoService {

    private resourceUrl = SERVER_API_URL + 'api/au-resto-order-statuses';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/au-resto-order-statuses';

    constructor(private http: Http) { }

    create(auRestoOrderStatus: AuRestoOrderStatusAuResto): Observable<AuRestoOrderStatusAuResto> {
        const copy = this.convert(auRestoOrderStatus);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(auRestoOrderStatus: AuRestoOrderStatusAuResto): Observable<AuRestoOrderStatusAuResto> {
        const copy = this.convert(auRestoOrderStatus);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<AuRestoOrderStatusAuResto> {
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
     * Convert a returned JSON object to AuRestoOrderStatusAuResto.
     */
    private convertItemFromServer(json: any): AuRestoOrderStatusAuResto {
        const entity: AuRestoOrderStatusAuResto = Object.assign(new AuRestoOrderStatusAuResto(), json);
        return entity;
    }

    /**
     * Convert a AuRestoOrderStatusAuResto to a JSON which can be sent to the server.
     */
    private convert(auRestoOrderStatus: AuRestoOrderStatusAuResto): AuRestoOrderStatusAuResto {
        const copy: AuRestoOrderStatusAuResto = Object.assign({}, auRestoOrderStatus);
        return copy;
    }
}
