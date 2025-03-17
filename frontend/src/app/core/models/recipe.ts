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
  ingredient: Set<Ingredient>;
  type: Set<recipeType>;
}
