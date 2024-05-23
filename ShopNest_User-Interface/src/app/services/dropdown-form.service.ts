import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Country } from '../common/country';
import { State } from '../common/state';

@Injectable({
  providedIn: 'root'
})
export class DropdownFormService {

  private prodUrl='https://shopnestapp.onrender.com'
  private baseUrl='http://localhost:8080'

  constructor(private httpClient:HttpClient) { }

  public getCountries():Observable<Country[]>{
     return this.httpClient.get<Country[]>(`${this.prodUrl}/api/countries`);
   }
 
   public getStates(code:string):Observable<State[]>{
     return this.httpClient.get<State[]>(`${this.prodUrl}/api/states/search/findByCountryCode/${code}`);
   }

   //used observable bcz angualr comp can subscribe to this method and get async call results
   getCreditCardMonths(startMonth:number):Observable<number[]>{
    let data:number[]=[]
    //build an array for months
    //start at curret month and loop untill
    for(let theMonth=startMonth;theMonth<=12;theMonth++)
    {
      data.push(theMonth);
    }
    return of(data);
  }

  getCreditCardYears():Observable<number[]>{
    let data:number[]=[]
    //build an array for years
    //start at curret year and loop untill
    const startYear:number=new Date().getFullYear();
    const endYear:number=startYear+10;
    for(let theYear=startYear;theYear<=endYear;theYear++)
    {
      data.push(theYear);
    }
    return of(data);
  }

}
