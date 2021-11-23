import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {IRecipe} from '../components/recipe/recipe';
import {catchError, tap} from 'rxjs/operators';
import {Ingredient} from '../components/recipe/ingredient';
import {AppConstants} from "../app.constants";

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  constructor(private http: HttpClient) { }

  getAllReceipes(): Observable<IRecipe[]> {
    return this.http.get<IRecipe[]>(AppConstants.API_BASE_URL + '/api/recipes')
    .pipe(
        tap(data => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
    );
  }

  getAllIngredients(id: number): Observable<Ingredient[]> {
    return this.http.get<Ingredient[]>(AppConstants.API_BASE_URL + '/api/ingredients/recipe/' + id)
    .pipe(
        tap(data => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
    );
  }

  getRecipe(id: number): Observable<IRecipe | undefined> {
    return this.http.get<IRecipe>(AppConstants.API_BASE_URL  +'/api/recipes/' + id)
    .pipe(
        tap(data => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
    );
  }

  private handleError(err: HttpErrorResponse): Observable<never> {
    // in a real world app, we may send the server to some remote logging infrastructure
    // instead of just logging it to the console
    let errorMessage = '';
    if (err.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      errorMessage = `An error occurred: ${err.error.message}`;
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      errorMessage = `Server returned code: ${err.status}, error message is: ${err.message}`;
    }
    console.error(errorMessage);
    return throwError(errorMessage);
  }
}
