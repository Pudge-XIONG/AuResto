import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { AuRestoUserAuResto } from './au-resto-user-au-resto.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AuRestoUserAuRestoService {

    private resourceUrl = SERVER_API_URL + 'api/au-resto-users';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/au-resto-users';

    constructor(private http: Http) { }

    create(auRestoUser: AuRestoUserAuResto): Observable<AuRestoUserAuResto> {
        const copy = this.convert(auRestoUser);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(auRestoUser: AuRestoUserAuResto): Observable<AuRestoUserAuResto> {
        const copy = this.convert(auRestoUser);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<AuRestoUserAuResto> {
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
     * Convert a returned JSON object to AuRestoUserAuResto.
     */
    private convertItemFromServer(json: any): AuRestoUserAuResto {
        const entity: AuRestoUserAuResto = Object.assign(new AuRestoUserAuResto(), json);
        return entity;
    }

    /**
     * Convert a AuRestoUserAuResto to a JSON which can be sent to the server.
     */
    private convert(auRestoUser: AuRestoUserAuResto): AuRestoUserAuResto {
        const copy: AuRestoUserAuResto = Object.assign({}, auRestoUser);
        return copy;
    }
}
