import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {AppConstants} from "./app.constants";

@Injectable()
export class AppService {

  authenticated = false;

  constructor(private http: HttpClient) {
  }

  authenticate(credentials, callback) {

        const headers = new HttpHeaders(credentials ? {
            authorization : 'Basic ' + btoa(credentials.username + ':' + credentials.password)
        } : {});

        this.http.get(AppConstants.API_BASE_URL + '/api/user', {headers}).subscribe(response => {
          if (response['name']) {
                this.authenticated = true;
            } else {
                console.log('Not ' + response['name']);
                this.authenticated = false;
            }
            return callback && callback();
        });

    }

}
