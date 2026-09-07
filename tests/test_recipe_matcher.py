from __future__ import annotations

import tempfile
import time
import unittest
from pathlib import Path

from smart_recipe_matcher.matching_engine import RecipeMatchingEngine
from smart_recipe_matcher.meal_planner import MealPlannerService
from smart_recipe_matcher.models import IngredientStock, Recipe, RecipeIngredient
from smart_recipe_matcher.pantry_recipe_management import PantryRecipeService
from smart_recipe_matcher.persistence import DataCorruptionError, JsonStorage


class SmartRecipeMatcherTests(unittest.TestCase):
    def setUp(self) -> None:
        self.tmp_dir = tempfile.TemporaryDirectory()
        self.data_file = Path(self.tmp_dir.name) / "data.json"
        self.storage = JsonStorage(str(self.data_file))
        self.service = PantryRecipeService(self.storage)
        self.planner = MealPlannerService(self.storage)
        self.matcher = RecipeMatchingEngine()

    def tearDown(self) -> None:
        self.tmp_dir.cleanup()

    def test_negative_ingredient_quantity_rejected(self) -> None:
        with self.assertRaises(ValueError):
            self.service.add_ingredient(IngredientStock("Milk", -1, "cup"))

    def test_duplicate_ingredient_rejected(self) -> None:
        self.service.add_ingredient(IngredientStock("Milk", 2, "cup"))
        with self.assertRaises(ValueError):
            self.service.add_ingredient(IngredientStock("milk", 1, "cup"))

    def test_corrupted_data_file_raises_clear_error(self) -> None:
        self.data_file.write_text("{bad json", encoding="utf-8")
        with self.assertRaises(DataCorruptionError):
            self.storage.load()

    def test_recipe_matching_ranking_with_filter_and_hints(self) -> None:
        pantry = [IngredientStock("Milk", 2, "cup"), IngredientStock("Flour", 1, "cup")]
        recipes = [
            Recipe("Pancakes", [RecipeIngredient("Milk", 1, "cup"), RecipeIngredient("Egg", 1, "each")], ["vegetarian"]),
            Recipe("Flatbread", [RecipeIngredient("Flour", 1, "cup")], ["vegan"]),
        ]

        results = self.matcher.rank_recipes(pantry, recipes, dietary_filters={"vegan"})
        self.assertEqual(len(results), 1)
        self.assertEqual(results[0].recipe_name, "Flatbread")
        self.assertEqual(results[0].match_percent, 100.0)

        unfiltered = self.matcher.rank_recipes(pantry, recipes)
        pancakes = next(r for r in unfiltered if r.recipe_name == "Pancakes")
        self.assertEqual(pancakes.missing_items_count, 1)
        self.assertIn("Egg", pancakes.substitution_hints)

    def test_matching_performance_1000_recipes_under_100ms(self) -> None:
        pantry = [IngredientStock("Rice", 100, "g"), IngredientStock("Beans", 100, "g")]
        recipes = [
            Recipe(f"Recipe {i}", [RecipeIngredient("Rice", 10, "g"), RecipeIngredient("Beans", 10, "g")], ["vegan"])
            for i in range(1000)
        ]
        start = time.perf_counter()
        self.matcher.rank_recipes(pantry, recipes)
        duration_ms = (time.perf_counter() - start) * 1000
        self.assertLess(duration_ms, 100)

    def test_weekly_plan_and_shopping_list_generation(self) -> None:
        self.service.add_ingredient(IngredientStock("Tomato", 1, "each"))
        self.planner.assign_meal("monday", "breakfast", "Toast")
        self.assertIn("Monday:", self.planner.weekly_plan_summary())

        recipes = [Recipe("Salad", [RecipeIngredient("Tomato", 2, "each"), RecipeIngredient("Cucumber", 1, "each")])]
        shopping = self.planner.generate_shopping_list(self.service.list_ingredients(), recipes)
        self.assertEqual(shopping["tomato"]["quantity"], 1.0)
        self.assertEqual(shopping["cucumber"]["quantity"], 1.0)


if __name__ == "__main__":
    unittest.main()
