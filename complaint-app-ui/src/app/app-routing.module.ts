import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ComplaintComponent } from './components/complaint/complaint.component';
import { LoginComponent } from './login.component';
import {ComplaintDetailGuard} from './components/complaint/complaint-detail.guard';
import {ComplaintDetailComponent} from './components/complaint/complaint-detail.component';


const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'complaints'},
  { path: 'complaints', component: ComplaintComponent},
  {
    path: 'complaints/:id',
    canActivate: [ComplaintDetailGuard],
    component: ComplaintDetailComponent
  },
  { path: 'login', component: LoginComponent}
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
