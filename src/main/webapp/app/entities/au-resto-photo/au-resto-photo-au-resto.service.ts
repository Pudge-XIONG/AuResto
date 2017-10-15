import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { AuRestoPhotoAuResto } from './au-resto-photo-au-resto.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AuRestoPhotoAuRestoService {

    private resourceUrl = SERVER_API_URL + 'api/au-resto-photos';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/au-resto-photos';

    constructor(private http: Http) { }

    create(auRestoPhoto: AuRestoPhotoAuResto): Observable<AuRestoPhotoAuResto> {
        const copy = this.convert(auRestoPhoto);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(auRestoPhoto: AuRestoPhotoAuResto): Observable<AuRestoPhotoAuResto> {
        const copy = this.convert(auRestoPhoto);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<AuRestoPhotoAuResto> {
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
     * Convert a returned JSON object to AuRestoPhotoAuResto.
     */
    private convertItemFromServer(json: any): AuRestoPhotoAuResto {
        const entity: AuRestoPhotoAuResto = Object.assign(new AuRestoPhotoAuResto(), json);
        return entity;
    }

    /**
     * Convert a AuRestoPhotoAuResto to a JSON which can be sent to the server.
     */
    private convert(auRestoPhoto: AuRestoPhotoAuResto): AuRestoPhotoAuResto {
        const copy: AuRestoPhotoAuResto = Object.assign({}, auRestoPhoto);
        return copy;
    }
}
