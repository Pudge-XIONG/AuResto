import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { AuRestoGenderAuResto } from './au-resto-gender-au-resto.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AuRestoGenderAuRestoService {

    private resourceUrl = SERVER_API_URL + 'api/au-resto-genders';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/au-resto-genders';

    constructor(private http: Http) { }

    create(auRestoGender: AuRestoGenderAuResto): Observable<AuRestoGenderAuResto> {
        const copy = this.convert(auRestoGender);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(auRestoGender: AuRestoGenderAuResto): Observable<AuRestoGenderAuResto> {
        const copy = this.convert(auRestoGender);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<AuRestoGenderAuResto> {
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
     * Convert a returned JSON object to AuRestoGenderAuResto.
     */
    private convertItemFromServer(json: any): AuRestoGenderAuResto {
        const entity: AuRestoGenderAuResto = Object.assign(new AuRestoGenderAuResto(), json);
        return entity;
    }

    /**
     * Convert a AuRestoGenderAuResto to a JSON which can be sent to the server.
     */
    private convert(auRestoGender: AuRestoGenderAuResto): AuRestoGenderAuResto {
        const copy: AuRestoGenderAuResto = Object.assign({}, auRestoGender);
        return copy;
    }
}
