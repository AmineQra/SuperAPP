import { Ingredient } from './ingredient';

enum recipeType {
  PETIT_DEJEUNER,
  DEJEUNER,
  DINER,
  GOUTER,
}

export interface Recipe {
  id: number;
  name: string;
  description: string;
  ingredients: Set<Ingredient>;
  types: Set<recipeType>;
}
