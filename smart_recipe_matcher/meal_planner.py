from __future__ import annotations

from collections import defaultdict
from typing import Dict, List

from .models import IngredientStock, Recipe
from .persistence import JsonStorage

VALID_SLOTS = {"breakfast", "lunch", "dinner"}


class MealPlannerService:
    def __init__(self, storage: JsonStorage) -> None:
        self.storage = storage

    def assign_meal(self, day: str, slot: str, recipe_name: str) -> None:
        if slot.strip().lower() not in VALID_SLOTS:
            raise ValueError("Slot must be Breakfast, Lunch, or Dinner.")
        data = self.storage.load()
        meal_plan = data["meal_plan"]
        normalized_day = day.strip().capitalize()
        meal_plan.setdefault(normalized_day, {})[slot.strip().lower()] = recipe_name.strip()
        self.storage.save(data)

    def weekly_plan_summary(self) -> str:
        plan = self.storage.load()["meal_plan"]
        if not plan:
            return "No meals scheduled."
        lines: List[str] = []
        for day in sorted(plan.keys()):
            lines.append(f"{day}:")
            for slot in ["breakfast", "lunch", "dinner"]:
                value = plan[day].get(slot, "-")
                lines.append(f"  {slot.capitalize()}: {value}")
        return "\n".join(lines)

    def generate_shopping_list(self, pantry: List[IngredientStock], recipes: List[Recipe]) -> Dict[str, Dict[str, float | str]]:
        pantry_index = {item.name.strip().lower(): item for item in pantry}
        needed: Dict[str, Dict[str, float | str]] = defaultdict(lambda: {"quantity": 0.0, "unit": ""})

        for recipe in recipes:
            for ingredient in recipe.ingredients:
                key = ingredient.name.strip().lower()
                current = pantry_index.get(key)
                missing_qty = ingredient.quantity
                if current and current.unit.strip().lower() == ingredient.unit.strip().lower():
                    missing_qty = max(0.0, ingredient.quantity - current.quantity)
                if missing_qty > 0:
                    needed[key]["quantity"] += missing_qty
                    needed[key]["unit"] = ingredient.unit

        return dict(needed)
