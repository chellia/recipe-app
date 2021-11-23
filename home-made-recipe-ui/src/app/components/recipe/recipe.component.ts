import { Component, OnInit } from '@angular/core';
import { RecipeService } from '../../services/recipe.service';
import {IRecipe} from './recipe';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-admin',
  templateUrl: './recipe.component.html',
  styleUrls: ['./recipe.component.css']
})
export class RecipeComponent implements OnInit {

  pageTitle = 'Recipe List';
  errorMessage = '';
  sub!: Subscription;
  // tslint:disable-next-line:variable-name
  private _listFilter = '';
  get listFilter(): string {
    return this._listFilter;
  }

  set listFilter(value: string) {
    this._listFilter = value;
    this.filteredRecipes = this.performFilter(value);
  }

  filteredRecipes: IRecipe[] = [];
  recipes: IRecipe[] = [];

  constructor(private recipeService: RecipeService) { }

  performFilter(filterBy: string): IRecipe[] {
    filterBy = filterBy.toLocaleLowerCase();
    return this.recipes.filter((recipe: IRecipe) =>
        recipe.name.toLocaleLowerCase().includes(filterBy));
  }

  ngOnInit(): void {
    this.sub = this.recipeService.getAllReceipes().subscribe({
      next: recipes => {
        this.recipes = recipes;
        this.filteredRecipes = this.recipes;
      },
      error: err => this.errorMessage = err
    });
  }

  // tslint:disable-next-line:use-lifecycle-interface
  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

}
