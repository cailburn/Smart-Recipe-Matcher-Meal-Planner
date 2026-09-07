from __future__ import annotations

from dataclasses import dataclass, field
from typing import Dict, List


@dataclass(frozen=True)
class IngredientStock:
    name: str
    quantity: float
    unit: str


@dataclass(frozen=True)
class RecipeIngredient:
    name: str
    quantity: float
    unit: str


@dataclass(frozen=True)
class Recipe:
    name: str
    ingredients: List[RecipeIngredient]
    dietary_tags: List[str] = field(default_factory=list)


@dataclass(frozen=True)
class MatchResult:
    recipe_name: str
    match_percent: float
    missing_items_count: int
    missing_items: List[str]
    substitution_hints: Dict[str, str]
