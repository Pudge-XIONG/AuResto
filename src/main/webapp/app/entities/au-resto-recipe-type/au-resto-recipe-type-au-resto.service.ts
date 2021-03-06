import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { AuRestoRecipeTypeAuResto } from './au-resto-recipe-type-au-resto.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AuRestoRecipeTypeAuRestoService {

    private resourceUrl = SERVER_API_URL + 'api/au-resto-recipe-types';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/au-resto-recipe-types';

    constructor(private http: Http) { }

    create(auRestoRecipeType: AuRestoRecipeTypeAuResto): Observable<AuRestoRecipeTypeAuResto> {
        const copy = this.convert(auRestoRecipeType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(auRestoRecipeType: AuRestoRecipeTypeAuResto): Observable<AuRestoRecipeTypeAuResto> {
        const copy = this.convert(auRestoRecipeType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<AuRestoRecipeTypeAuResto> {
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
     * Convert a returned JSON object to AuRestoRecipeTypeAuResto.
     */
    private convertItemFromServer(json: any): AuRestoRecipeTypeAuResto {
        const entity: AuRestoRecipeTypeAuResto = Object.assign(new AuRestoRecipeTypeAuResto(), json);
        return entity;
    }

    /**
     * Convert a AuRestoRecipeTypeAuResto to a JSON which can be sent to the server.
     */
    private convert(auRestoRecipeType: AuRestoRecipeTypeAuResto): AuRestoRecipeTypeAuResto {
        const copy: AuRestoRecipeTypeAuResto = Object.assign({}, auRestoRecipeType);
        return copy;
    }
}
