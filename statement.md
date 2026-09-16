# Project Statement: Smart Recipe Matcher & Meal Planner

## 1. Problem Statement
Household food waste is a growing issue, often occurring because individuals do not know what recipes they can prepare using the ingredients they already have in their kitchen. Additionally, manual meal planning and figuring out what groceries are missing for a specific recipe is tedious, leading to over-purchasing and spoiled food.

## 2. Scope of the Project
This project provides a backend Java application with an interactive Command Line Interface (CLI) that acts as a kitchen assistant. It focuses on in-memory data processing to:
* Track existing pantry inventory.
* Cross-reference available inventory with a catalog of recipes.
* Execute a matching algorithm to rank recipes by feasibility.
* Identify missing ingredients.

*(Note: External database persistence and graphical front-end GUIs are outside the scope of this current implementation).*

## 3. Target Users
* **College Students / Hostellers:** Looking to cook meals with limited available ingredients.
* **Working Professionals:** Needing quick meal ideas without spending time manually cross-referencing pantry items.
* **Budget-conscious Home Cooks:** Wanting to reduce food waste and avoid buying duplicate groceries.

## 4. High-Level Features
* **Inventory Tracking System:** Add, update, and view ingredients currently in stock.
* **Algorithmic Match Scoring:** Computes `(Available Ingredients / Required Ingredients) * 100` to find optimal meals.
* **Delta/Missing Items Generator:** Compares ingredient sets to isolate exact missing items for grocery planning.
* **Data Seed Engine:** Automatically loads default recipes and ingredients upon application startup for seamless testing.

## High-Level Features
*   **Inventory Tracking System:** Add and view ingredients currently in stock[cite: 1].
*   **Algorithmic Match Scoring:** Computes match percentage to find optimal meals[cite: 1].
*   **Delta/Missing Items Generator:** Compares sets to output exact missing ingredients for shopping purposes[cite: 1].
*   **Data Seed Engine:** Automatically loads default recipes and ingredients upon application startup for seamless testing[cite: 1].
