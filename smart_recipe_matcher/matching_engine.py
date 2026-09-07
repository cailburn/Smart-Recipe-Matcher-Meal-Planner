from __future__ import annotations

from typing import Dict, Iterable, List, Optional, Set

from .models import IngredientStock, MatchResult, Recipe

SUBSTITUTIONS = {
    "milk": "Use oat milk or almond milk.",
    "egg": "Use flaxseed meal + water.",
    "butter": "Use olive oil or coconut oil.",
    "flour": "Use gluten-free all-purpose flour.",
}


class RecipeMatchingEngine:
    def rank_recipes(
        self,
        pantry: Iterable[IngredientStock],
        recipes: Iterable[Recipe],
        dietary_filters: Optional[Set[str]] = None,
    ) -> List[MatchResult]:
        pantry_index: Dict[str, IngredientStock] = {item.name.strip().lower(): item for item in pantry}
        normalized_filters = {f.lower() for f in dietary_filters} if dietary_filters else None
        results: List[MatchResult] = []

        for recipe in recipes:
            tags = {tag.lower() for tag in recipe.dietary_tags}
            if normalized_filters and not normalized_filters.issubset(tags):
                continue

            missing: List[str] = []
            substitutions: Dict[str, str] = {}
            matched_count = 0

            for needed in recipe.ingredients:
                key = needed.name.strip().lower()
                existing = pantry_index.get(key)
                if existing and existing.unit.strip().lower() == needed.unit.strip().lower() and existing.quantity >= needed.quantity:
                    matched_count += 1
                else:
                    missing.append(needed.name)
                    if key in SUBSTITUTIONS:
                        substitutions[needed.name] = SUBSTITUTIONS[key]

            total = len(recipe.ingredients)
            percent = round((matched_count / total) * 100, 2) if total else 0.0
            results.append(
                MatchResult(
                    recipe_name=recipe.name,
                    match_percent=percent,
                    missing_items_count=len(missing),
                    missing_items=missing,
                    substitution_hints=substitutions,
                )
            )

        return sorted(results, key=lambda r: (-r.match_percent, r.missing_items_count, r.recipe_name.lower()))
