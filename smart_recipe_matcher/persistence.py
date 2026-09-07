from __future__ import annotations

import json
from pathlib import Path
from typing import Any, Dict


class DataCorruptionError(Exception):
    pass


class JsonStorage:
    def __init__(self, file_path: str) -> None:
        self.path = Path(file_path)

    def load(self) -> Dict[str, Any]:
        if not self.path.exists():
            return {"pantry": {}, "recipes": {}, "meal_plan": {}}
        try:
            with self.path.open("r", encoding="utf-8") as f:
                data = json.load(f)
        except json.JSONDecodeError as exc:
            raise DataCorruptionError("Stored data is corrupted and could not be read.") from exc
        if not isinstance(data, dict):
            raise DataCorruptionError("Stored data must be a JSON object.")
        return {
            "pantry": data.get("pantry", {}),
            "recipes": data.get("recipes", {}),
            "meal_plan": data.get("meal_plan", {}),
        }

    def save(self, data: Dict[str, Any]) -> None:
        self.path.parent.mkdir(parents=True, exist_ok=True)
        with self.path.open("w", encoding="utf-8") as f:
            json.dump(data, f, indent=2, sort_keys=True)
