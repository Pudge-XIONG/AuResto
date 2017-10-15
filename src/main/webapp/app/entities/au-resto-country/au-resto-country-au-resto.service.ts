import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { AuRestoCountryAuResto } from './au-resto-country-au-resto.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AuRestoCountryAuRestoService {

    private resourceUrl = SERVER_API_URL + 'api/au-resto-countries';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/au-resto-countries';

    constructor(private http: Http) { }

    create(auRestoCountry: AuRestoCountryAuResto): Observable<AuRestoCountryAuResto> {
        const copy = this.convert(auRestoCountry);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(auRestoCountry: AuRestoCountryAuResto): Observable<AuRestoCountryAuResto> {
        const copy = this.convert(auRestoCountry);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<AuRestoCountryAuResto> {
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
     * Convert a returned JSON object to AuRestoCountryAuResto.
     */
    private convertItemFromServer(json: any): AuRestoCountryAuResto {
        const entity: AuRestoCountryAuResto = Object.assign(new AuRestoCountryAuResto(), json);
        return entity;
    }

    /**
     * Convert a AuRestoCountryAuResto to a JSON which can be sent to the server.
     */
    private convert(auRestoCountry: AuRestoCountryAuResto): AuRestoCountryAuResto {
        const copy: AuRestoCountryAuResto = Object.assign({}, auRestoCountry);
        return copy;
    }
}
