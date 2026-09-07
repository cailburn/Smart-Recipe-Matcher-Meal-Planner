# Smart-Recipe-Matcher-Meal-Planner

A modular CLI app with three functional modules:

1. **Pantry & Recipe Management (CRUD)**
   - Add/update/remove pantry ingredients with quantity and units
   - Add and list recipes with dietary tags
2. **Recipe Matching Engine (Data Processing)**
   - Computes per-recipe match percentage from pantry inventory
   - Supports dietary filters and substitution hints for missing items
3. **Meal Planner & Shopping List Generator (Reporting)**
   - Assign recipes to weekly meal slots (Breakfast/Lunch/Dinner)
   - Produces weekly plan summaries and consolidated shopping list output

## Non-functional requirement coverage

- **Usability**: CLI commands with argument validation and clear success/error messages.
- **Performance**: Matching engine is covered by tests validating sub-100ms ranking for 1,000 recipes.
- **Maintainability & Modularity**: Data models, persistence, and business services are separated by module.
- **Error Handling & Reliability**: Handles duplicate entries, invalid quantities, invalid slots, and corrupted data files without crashes.

## Run

```bash
python -m smart_recipe_matcher.cli --help
python -m unittest discover -s tests -v
```
