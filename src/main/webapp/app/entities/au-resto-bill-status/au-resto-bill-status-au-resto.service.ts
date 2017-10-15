import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { AuRestoBillStatusAuResto } from './au-resto-bill-status-au-resto.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AuRestoBillStatusAuRestoService {

    private resourceUrl = SERVER_API_URL + 'api/au-resto-bill-statuses';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/au-resto-bill-statuses';

    constructor(private http: Http) { }

    create(auRestoBillStatus: AuRestoBillStatusAuResto): Observable<AuRestoBillStatusAuResto> {
        const copy = this.convert(auRestoBillStatus);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(auRestoBillStatus: AuRestoBillStatusAuResto): Observable<AuRestoBillStatusAuResto> {
        const copy = this.convert(auRestoBillStatus);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<AuRestoBillStatusAuResto> {
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
     * Convert a returned JSON object to AuRestoBillStatusAuResto.
     */
    private convertItemFromServer(json: any): AuRestoBillStatusAuResto {
        const entity: AuRestoBillStatusAuResto = Object.assign(new AuRestoBillStatusAuResto(), json);
        return entity;
    }

    /**
     * Convert a AuRestoBillStatusAuResto to a JSON which can be sent to the server.
     */
    private convert(auRestoBillStatus: AuRestoBillStatusAuResto): AuRestoBillStatusAuResto {
        const copy: AuRestoBillStatusAuResto = Object.assign({}, auRestoBillStatus);
        return copy;
    }
}
