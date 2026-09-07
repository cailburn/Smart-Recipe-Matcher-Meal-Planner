# Smart-Recipe-Matcher-Meal-Planner
## Overview of the Project
- The Smart Recipe Matcher & Meal Planner is a Java-based application designed to help users minimize household food waste and simplify their weekly meal prep. Often, people struggle to figure out what they can cook with the ingredients they already have in their pantry.
- This system solves that problem by tracking available kitchen inventory and intelligently matching it against a catalog of recipes.
- Furthermore, it allows users to schedule their matched meals onto a weekly calendar and automatically generates a consolidated grocery list for any missing ingredients. 
---
## Features
This system is built around three major functional modules:
* **Pantry & Recipe Management (CRUD):** Users can add, update, and manage their current pantry ingredients and create detailed recipes with specific dietary tags.
* **Recipe Matching Engine (Data Processing):** The core algorithm cross-references the user's available ingredients with the recipe catalog to calculate match percentages and identify missing items.
* **Meal Planner & Shopping List Generator (Reporting):** Users can assign recipes to specific days/meals, and the system aggregates all missing components into an exportable weekly shopping list.
---

## Technologies/Tools Used
* Java (JDK 17 or higher)
* Standard Java Collections Framework
* Git & GitHub for version control

## Steps to Install & Run the Project
1. Open your terminal or command prompt.
2. Clone this repository to your local machine:
   `git clone https://github.com/cailburn/Smart-Recipe-Matcher-Meal-Planner`
3. Navigate into the project directory:
   `cd SmartRecipeMatcher/src`
4. Compile the application:
   `javac Main.java`
5. Run the application:
   `java Main`

## Instructions for Testing
To validate the system's core functional modules, follow these test cases:
1. **Pantry CRUD Test:** Launch the app and add 3 ingredients (e.g., 2 lbs Chicken, 1 cup Rice, 3 oz Soy Sauce). Verify they save correctly. Try entering a negative quantity to test the error handling.
2. **Matching Engine Test:** Create a recipe that requires Chicken, Rice, and Broccoli. Run the matching algorithm and verify it returns a ~66% match, explicitly listing "Broccoli" as the missing ingredient.
3. **Meal Plan Test:** Assign the matched recipe to a specific day in the meal planner, and verify that the generated shopping list accurately outputs the missing broccoli.

