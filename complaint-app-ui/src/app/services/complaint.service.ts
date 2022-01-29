import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {IComplaint} from '../components/complaint/complaint';
import {catchError, tap} from 'rxjs/operators';
import {User} from '../components/complaint/user';
import {AppConstants} from "../app.constants";

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ComplaintService {

  constructor(private http: HttpClient) { }

  getAllComplaints(): Observable<IComplaint[]> {
    return this.http.get<IComplaint[]>(AppConstants.API_BASE_URL + '/api/complaints')
    .pipe(
        tap(data => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
    );
  }

  getAllUsers(id: number): Observable<User> {
    return this.http.get<User>(AppConstants.API_BASE_URL + '/api/users/complaint/' + id)
    .pipe(
        tap(data => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
    );
  }

  getComplaint(id: number): Observable<IComplaint | undefined> {
    return this.http.get<IComplaint>(AppConstants.API_BASE_URL  +'/api/complaints/' + id)
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
