import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs'; 


export interface PaymentOrder {
    orderId: string;
    amount: number;
    currency: string;
    keyId: string;
}

@Injectable({
    providedIn: 'root'
})
export class PaymentService {

    private readonly baseUrl = '/api/payments';

    constructor(private http: HttpClient) { }   

    createOrder(): Observable<PaymentOrder> {
        
        return this.http.post<PaymentOrder>(`${this.baseUrl}/order`, {});
    }


}
