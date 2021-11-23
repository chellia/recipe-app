import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RecipeComponent } from './components/recipe/recipe.component';
import { LoginComponent } from './login.component';
import {RecipeDetailGuard} from './components/recipe/recipe-detail.guard';
import {RecipeDetailComponent} from './components/recipe/recipe-detail.component';


const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'recipes'},
  { path: 'recipes', component: RecipeComponent},
  {
    path: 'recipes/:id',
    canActivate: [RecipeDetailGuard],
    component: RecipeDetailComponent
  },
  { path: 'login', component: LoginComponent}
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
