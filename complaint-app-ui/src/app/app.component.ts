import { Component } from '@angular/core';
import {AppService} from './app.service';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Router} from '@angular/router';
import {Observable, Subscription, throwError} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {AppConstants} from "./app.constants";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ComplaintUI';
  sub!: Subscription;

  constructor(private app: AppService, private http: HttpClient, private router: Router) {
    this.app.authenticate(undefined, undefined);
  }

  logout() {
    this.sub = this.http.post(AppConstants.API_BASE_URL + '/api/logout', {})
    .subscribe( response => {
          this.app.authenticated = false;
          this.router.navigate(['/login']);
     }
    );
  }

  // tslint:disable-next-line:use-lifecycle-interface
  ngOnDestroy(): void {
    if(this.sub){
      this.sub.unsubscribe();
    }
  }

}
