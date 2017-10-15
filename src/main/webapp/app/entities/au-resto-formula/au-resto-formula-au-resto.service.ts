import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { AuRestoFormulaAuResto } from './au-resto-formula-au-resto.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AuRestoFormulaAuRestoService {

    private resourceUrl = SERVER_API_URL + 'api/au-resto-formulas';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/au-resto-formulas';

    constructor(private http: Http) { }

    create(auRestoFormula: AuRestoFormulaAuResto): Observable<AuRestoFormulaAuResto> {
        const copy = this.convert(auRestoFormula);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(auRestoFormula: AuRestoFormulaAuResto): Observable<AuRestoFormulaAuResto> {
        const copy = this.convert(auRestoFormula);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<AuRestoFormulaAuResto> {
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
     * Convert a returned JSON object to AuRestoFormulaAuResto.
     */
    private convertItemFromServer(json: any): AuRestoFormulaAuResto {
        const entity: AuRestoFormulaAuResto = Object.assign(new AuRestoFormulaAuResto(), json);
        return entity;
    }

    /**
     * Convert a AuRestoFormulaAuResto to a JSON which can be sent to the server.
     */
    private convert(auRestoFormula: AuRestoFormulaAuResto): AuRestoFormulaAuResto {
        const copy: AuRestoFormulaAuResto = Object.assign({}, auRestoFormula);
        return copy;
    }
}
