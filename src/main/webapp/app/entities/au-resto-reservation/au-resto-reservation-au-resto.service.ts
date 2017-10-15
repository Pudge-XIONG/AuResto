import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { AuRestoReservationAuResto } from './au-resto-reservation-au-resto.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AuRestoReservationAuRestoService {

    private resourceUrl = SERVER_API_URL + 'api/au-resto-reservations';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/au-resto-reservations';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(auRestoReservation: AuRestoReservationAuResto): Observable<AuRestoReservationAuResto> {
        const copy = this.convert(auRestoReservation);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(auRestoReservation: AuRestoReservationAuResto): Observable<AuRestoReservationAuResto> {
        const copy = this.convert(auRestoReservation);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<AuRestoReservationAuResto> {
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
     * Convert a returned JSON object to AuRestoReservationAuResto.
     */
    private convertItemFromServer(json: any): AuRestoReservationAuResto {
        const entity: AuRestoReservationAuResto = Object.assign(new AuRestoReservationAuResto(), json);
        entity.reserveDate = this.dateUtils
            .convertDateTimeFromServer(json.reserveDate);
        entity.reserveForDate = this.dateUtils
            .convertDateTimeFromServer(json.reserveForDate);
        return entity;
    }

    /**
     * Convert a AuRestoReservationAuResto to a JSON which can be sent to the server.
     */
    private convert(auRestoReservation: AuRestoReservationAuResto): AuRestoReservationAuResto {
        const copy: AuRestoReservationAuResto = Object.assign({}, auRestoReservation);

        copy.reserveDate = this.dateUtils.toDate(auRestoReservation.reserveDate);

        copy.reserveForDate = this.dateUtils.toDate(auRestoReservation.reserveForDate);
        return copy;
    }
}
