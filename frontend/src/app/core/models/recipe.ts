import { Ingredient } from './ingredient';

export enum RecipeType {
  MATIN,
  MIDI,
  SOIR,
  GOUTER,
}

export interface Recipe {
  id: number | null;
  name: string;
  description: string;
  ingredients: number[];
  types: RecipeType[];
  img: string;
}
