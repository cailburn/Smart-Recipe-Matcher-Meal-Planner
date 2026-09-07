from __future__ import annotations

from typing import Dict, List

from .models import IngredientStock, Recipe, RecipeIngredient
from .persistence import JsonStorage


class PantryRecipeService:
    def __init__(self, storage: JsonStorage) -> None:
        self.storage = storage

    def add_ingredient(self, ingredient: IngredientStock) -> None:
        self._validate_quantity(ingredient.quantity)
        data = self.storage.load()
        key = ingredient.name.strip().lower()
        if key in data["pantry"]:
            raise ValueError(f"Ingredient '{ingredient.name}' already exists.")
        data["pantry"][key] = {
            "name": ingredient.name.strip(),
            "quantity": ingredient.quantity,
            "unit": ingredient.unit.strip(),
        }
        self.storage.save(data)

    def update_ingredient(self, ingredient: IngredientStock) -> None:
        self._validate_quantity(ingredient.quantity)
        data = self.storage.load()
        key = ingredient.name.strip().lower()
        if key not in data["pantry"]:
            raise ValueError(f"Ingredient '{ingredient.name}' does not exist.")
        data["pantry"][key] = {
            "name": ingredient.name.strip(),
            "quantity": ingredient.quantity,
            "unit": ingredient.unit.strip(),
        }
        self.storage.save(data)

    def remove_ingredient(self, ingredient_name: str) -> None:
        data = self.storage.load()
        key = ingredient_name.strip().lower()
        if key not in data["pantry"]:
            raise ValueError(f"Ingredient '{ingredient_name}' does not exist.")
        del data["pantry"][key]
        self.storage.save(data)

    def list_ingredients(self) -> List[IngredientStock]:
        pantry = self.storage.load()["pantry"]
        return [IngredientStock(v["name"], v["quantity"], v["unit"]) for v in pantry.values()]

    def add_recipe(self, recipe: Recipe) -> None:
        self._validate_recipe(recipe)
        data = self.storage.load()
        key = recipe.name.strip().lower()
        if key in data["recipes"]:
            raise ValueError(f"Recipe '{recipe.name}' already exists.")
        data["recipes"][key] = {
            "name": recipe.name.strip(),
            "dietary_tags": [tag.strip().lower() for tag in recipe.dietary_tags],
            "ingredients": [
                {"name": i.name.strip(), "quantity": i.quantity, "unit": i.unit.strip()}
                for i in recipe.ingredients
            ],
        }
        self.storage.save(data)

    def list_recipes(self) -> List[Recipe]:
        recipes = self.storage.load()["recipes"]
        return [
            Recipe(
                name=v["name"],
                dietary_tags=list(v.get("dietary_tags", [])),
                ingredients=[RecipeIngredient(i["name"], i["quantity"], i["unit"]) for i in v["ingredients"]],
            )
            for v in recipes.values()
        ]

    def _validate_quantity(self, quantity: float) -> None:
        if quantity <= 0:
            raise ValueError("Quantity must be greater than zero.")

    def _validate_recipe(self, recipe: Recipe) -> None:
        if not recipe.ingredients:
            raise ValueError("Recipe must include at least one ingredient.")
        for ingredient in recipe.ingredients:
            self._validate_quantity(ingredient.quantity)
