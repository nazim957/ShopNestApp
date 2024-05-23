import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OrderHistory } from '../common/order-history';

@Injectable({
  providedIn: 'root'
})
export class OrderHistoryService {

  private prodUrl='https://shopnestapp.onrender.com'
  private baseUrl='http://localhost:8080'

  constructor(private httpClient:HttpClient) { }

  public getOrderHistory(): Observable<OrderHistory[]>{

    return this.httpClient.get<OrderHistory[]>(`${this.prodUrl}/api/orders`)
  }
}