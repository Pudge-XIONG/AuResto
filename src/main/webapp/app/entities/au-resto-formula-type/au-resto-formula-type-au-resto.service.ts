import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { AuRestoFormulaTypeAuResto } from './au-resto-formula-type-au-resto.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AuRestoFormulaTypeAuRestoService {

    private resourceUrl = SERVER_API_URL + 'api/au-resto-formula-types';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/au-resto-formula-types';

    constructor(private http: Http) { }

    create(auRestoFormulaType: AuRestoFormulaTypeAuResto): Observable<AuRestoFormulaTypeAuResto> {
        const copy = this.convert(auRestoFormulaType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(auRestoFormulaType: AuRestoFormulaTypeAuResto): Observable<AuRestoFormulaTypeAuResto> {
        const copy = this.convert(auRestoFormulaType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<AuRestoFormulaTypeAuResto> {
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
     * Convert a returned JSON object to AuRestoFormulaTypeAuResto.
     */
    private convertItemFromServer(json: any): AuRestoFormulaTypeAuResto {
        const entity: AuRestoFormulaTypeAuResto = Object.assign(new AuRestoFormulaTypeAuResto(), json);
        return entity;
    }

    /**
     * Convert a AuRestoFormulaTypeAuResto to a JSON which can be sent to the server.
     */
    private convert(auRestoFormulaType: AuRestoFormulaTypeAuResto): AuRestoFormulaTypeAuResto {
        const copy: AuRestoFormulaTypeAuResto = Object.assign({}, auRestoFormulaType);
        return copy;
    }
}
