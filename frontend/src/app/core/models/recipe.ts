import { Ingredient } from './ingredient';

export interface Recipe {
  id: number;
  name: string;
  description: string;
  ingredient: Set<Ingredient>;
}
