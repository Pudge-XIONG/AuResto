import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { AuRestoRecipeAuResto } from './au-resto-recipe-au-resto.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AuRestoRecipeAuRestoService {

    private resourceUrl = SERVER_API_URL + 'api/au-resto-recipes';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/au-resto-recipes';

    constructor(private http: Http) { }

    create(auRestoRecipe: AuRestoRecipeAuResto): Observable<AuRestoRecipeAuResto> {
        const copy = this.convert(auRestoRecipe);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(auRestoRecipe: AuRestoRecipeAuResto): Observable<AuRestoRecipeAuResto> {
        const copy = this.convert(auRestoRecipe);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<AuRestoRecipeAuResto> {
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
     * Convert a returned JSON object to AuRestoRecipeAuResto.
     */
    private convertItemFromServer(json: any): AuRestoRecipeAuResto {
        const entity: AuRestoRecipeAuResto = Object.assign(new AuRestoRecipeAuResto(), json);
        return entity;
    }

    /**
     * Convert a AuRestoRecipeAuResto to a JSON which can be sent to the server.
     */
    private convert(auRestoRecipe: AuRestoRecipeAuResto): AuRestoRecipeAuResto {
        const copy: AuRestoRecipeAuResto = Object.assign({}, auRestoRecipe);
        return copy;
    }
}
