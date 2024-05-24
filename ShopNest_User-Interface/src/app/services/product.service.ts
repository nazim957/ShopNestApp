import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../common/product';
import { ProductCategory } from '../common/product-category';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private prodUrl='https://shopnestapp.onrender.com'
  private baseUrl='http://localhost:8080'


  constructor(private httpClient:HttpClient) { }

  getProductList(): Observable<Product[]>{
    return this.httpClient.get<Product[]>(`${this.prodUrl}/api/products`);
  }

  getProductCategories(): Observable<ProductCategory[]> {

    return this.httpClient.get<ProductCategory[]>(`${this.prodUrl}/api/category`)
  }

  getProductCategoriesbyName(hasCategoryName:string): Observable<Product[]> {

    return this.httpClient.get<Product[]>(`${this.prodUrl}/api/category/${hasCategoryName}`)
  }

  searchProducts(theKeyword: string): Observable<Product[]> {

    // need to build URL based on the keyword 
    const searchUrl = `${this.prodUrl}/api/products/search?name=${theKeyword}`;
    return this.httpClient.get<Product[]>(searchUrl);
  }

  getProduct(theProductId:number): Observable<Product> {
    return this.httpClient.get<Product>(`${this.prodUrl}/api/products/${theProductId}`)
  }

}
