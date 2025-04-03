import { Ingredient } from './ingredient';

export enum RecipeType {
  MATIN,
  MIDI,
  SOIR,
  GOUTER,
}

export interface Recipe {
  id: number;
  name: string;
  description: string;
  ingredients: Ingredient[];
  types: RecipeType[];
}
