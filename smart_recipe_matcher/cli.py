from __future__ import annotations

import argparse
from pathlib import Path

from .matching_engine import RecipeMatchingEngine
from .meal_planner import MealPlannerService
from .models import IngredientStock, Recipe, RecipeIngredient
from .pantry_recipe_management import PantryRecipeService
from .persistence import DataCorruptionError, JsonStorage


def build_parser() -> argparse.ArgumentParser:
    parser = argparse.ArgumentParser(description="Smart Recipe Matcher & Meal Planner")
    parser.add_argument("--data-file", default=str(Path("data") / "app_data.json"))

    sub = parser.add_subparsers(dest="command", required=True)

    add_ing = sub.add_parser("add-ingredient")
    add_ing.add_argument("name")
    add_ing.add_argument("quantity", type=float)
    add_ing.add_argument("unit")

    add_recipe = sub.add_parser("add-recipe")
    add_recipe.add_argument("name")
    add_recipe.add_argument("--ingredient", action="append", required=True, help="Format: name:quantity:unit")
    add_recipe.add_argument("--tag", action="append", default=[])

    sub.add_parser("match-recipes")

    assign = sub.add_parser("assign-meal")
    assign.add_argument("day")
    assign.add_argument("slot")
    assign.add_argument("recipe")

    sub.add_parser("weekly-plan")
    sub.add_parser("shopping-list")
    return parser


def parse_recipe_ingredients(values: list[str]) -> list[RecipeIngredient]:
    parsed: list[RecipeIngredient] = []
    for raw in values:
        try:
            name, quantity, unit = raw.split(":")
            parsed.append(RecipeIngredient(name=name, quantity=float(quantity), unit=unit))
        except ValueError as exc:
            raise ValueError("Ingredient must use name:quantity:unit format.") from exc
    return parsed


def main(argv: list[str] | None = None) -> int:
    args = build_parser().parse_args(argv)
    storage = JsonStorage(args.data_file)
    service = PantryRecipeService(storage)
    planner = MealPlannerService(storage)
    matcher = RecipeMatchingEngine()

    try:
        if args.command == "add-ingredient":
            service.add_ingredient(IngredientStock(args.name, args.quantity, args.unit))
            print(f"Added ingredient '{args.name}'.")
            return 0

        if args.command == "add-recipe":
            ingredients = parse_recipe_ingredients(args.ingredient)
            service.add_recipe(Recipe(name=args.name, ingredients=ingredients, dietary_tags=args.tag))
            print(f"Added recipe '{args.name}'.")
            return 0

        if args.command == "match-recipes":
            matches = matcher.rank_recipes(service.list_ingredients(), service.list_recipes())
            if not matches:
                print("No recipes available.")
            for match in matches:
                print(
                    f"{match.recipe_name}: {match.match_percent}% match, "
                    f"missing {match.missing_items_count} items"
                )
            return 0

        if args.command == "assign-meal":
            planner.assign_meal(args.day, args.slot, args.recipe)
            print(f"Assigned {args.recipe} to {args.day} {args.slot}.")
            return 0

        if args.command == "weekly-plan":
            print(planner.weekly_plan_summary())
            return 0

        if args.command == "shopping-list":
            shopping = planner.generate_shopping_list(service.list_ingredients(), service.list_recipes())
            if not shopping:
                print("No missing ingredients.")
            for item, info in sorted(shopping.items()):
                print(f"{item}: {info['quantity']} {info['unit']}")
            return 0
    except (ValueError, DataCorruptionError) as exc:
        print(f"Error: {exc}")
        return 1

    return 1


if __name__ == "__main__":
    raise SystemExit(main())
