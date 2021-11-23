import {Ingredient} from './ingredient';

export interface IRecipe {
  id: number;
  name: string;
  createdDateTime: string;
  vegetarian: boolean;
  servings: number;
  instructions: string;
  ingredients: Array<Ingredient>;
}
