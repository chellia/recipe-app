import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {IRecipe} from './recipe';
import {RecipeService} from '../../services/recipe.service';
import {Subscription} from 'rxjs';

@Component({
  templateUrl: './recipe-detail.component.html',
  styleUrls: ['./recipe-detail.component.css']
})
export class RecipeDetailComponent implements OnInit {
  pageTitle = 'Recipe Detail';
  errorMessage = '';
  recipe: IRecipe | undefined;
  sub!: Subscription;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private recipeService: RecipeService) {
  }

 /* ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.sub = this.recipeService.getRecipe(id).subscribe({
        next: recipe => this.recipe = recipe,
        error: err => this.errorMessage = err
      });
    }
  }*/

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.sub = this.recipeService.getRecipe(id).subscribe(recipe => {
        this.recipeService.getAllIngredients(id).subscribe(ingredients => {
          this.recipe = recipe;
          this.recipe.ingredients = ingredients;
        });
      });
    }
  }

  // tslint:disable-next-line:use-lifecycle-interface
  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  onBack(): void {
    this.router.navigate(['/recipes']);
  }
}
