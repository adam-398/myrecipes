package dev.auroralaboratories.myrecipes.databasefunctions

import com.google.firebase.crashlytics.FirebaseCrashlytics
import dev.auroralaboratories.myrecipes.databasefunctions.SupabaseClient.supabase
import dev.auroralaboratories.myrecipes.dataclasses.Cuisine
import dev.auroralaboratories.myrecipes.dataclasses.Ingredient
import dev.auroralaboratories.myrecipes.dataclasses.Recipe
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Order

/**
 * Adds a new recipe to the database.
 * @param title The title of the recipe.
 * @param cuisine The cuisine of the recipe.
 * @param description The description of the recipe.
 * @param instructions The instructions for the recipe.
 * @param servings The number of servings for the recipe.
 * @param prepTime The preparation time for the recipe in minutes.
 * @param cookTime The cooking time for the recipe in minutes.
 */
suspend fun addRecipe(
    title: String,
    cuisine: String? = null,
    description: String? = null,
    instructions: String? = null,
    servings: Int? = null,
    prepTime: Int? = null,
    cookTime: Int? = null,
    ingredients: List<Ingredient> = emptyList()
): String? {
    return try {
        val userId = supabase.auth.currentSessionOrNull()?.user?.id ?: return null

        val recipe = Recipe(
            user_id = userId,
            cuisine_id = cuisine,
            title = title,
            description = description,
            instructions = instructions,
            servings = servings,
            prep_time_minutes = prepTime,
            cook_time_minutes = cookTime,
            ingredients = ingredients
        )

        supabase.postgrest["recipes"]
            .insert(recipe) {
                select()
            }
            .decodeSingle<Recipe>()
            .id
    } catch (e: Exception) {
        FirebaseCrashlytics.getInstance().recordException(Exception("Error adding recipe: $e"))
        null
    }
}

/**
 * Fetches all recipes from the database.
 * @return A list of recipes.
 */
suspend fun fetchAllRecipes(): List<Recipe> {
    return try {
        val userId = supabase.auth.currentSessionOrNull()?.user?.id ?: return emptyList()
        supabase.postgrest["recipes"]
            .select {
                filter { eq("user_id", userId) }
            }
            .decodeList()
    } catch (e: Exception) {
        FirebaseCrashlytics.getInstance().recordException(Exception("Error fetching recipes: $e"))
        emptyList()
    }
}

/**
 * Fetches a recipe by its ID.
 * @param id The ID of the recipe.
 * @return The recipe with the specified ID.
 */
suspend fun fetchRecipeById(id: String): Recipe? {
    return try {
        supabase.postgrest["recipes"]
            .select {
                filter { eq("id", id) }
            }
            .decodeSingle<Recipe>()
    } catch (e: Exception) {
        FirebaseCrashlytics.getInstance().recordException(Exception("Error fetching items: $e"))
        null
    }
}

/**
 * Deletes a recipe in the database.
 * @param id The ID of the recipe to update.
 */
suspend fun deleteRecipeById(id: String): Boolean {
    return try {
        val userId = supabase.auth.currentSessionOrNull()?.user?.id ?: return false
        supabase.postgrest["recipes"]
            .delete {
                filter {
                    eq("user_id", userId)
                    eq("id", id)
                }
            }
        true
    } catch (e: Exception) {
        FirebaseCrashlytics.getInstance().recordException(Exception("Error deleting recipe: $e"))
        false
    }
}

/**
 * Updates a recipe in the database.
 * @param id The ID of the recipe to update.
 * @param updatedRecipe The updated recipe object.
 * @return True if the update was successful, false otherwise.
 */
suspend fun updateRecipeById(id: String, updatedRecipe: Recipe): Boolean {
    return try {
        val userId = supabase.auth.currentSessionOrNull()?.user?.id ?: return false
        supabase.postgrest["recipes"]
            .update(updatedRecipe) {
                filter {
                    eq("id", id)
                    eq("user_id", userId)
                }
            }
        true
    } catch (e: Exception) {
        FirebaseCrashlytics.getInstance().recordException(Exception("Error updating item: $e"))
        false
    }
}

/**
 * Fetches recipes by cuisine.
 * @param cuisineId The ID of the cuisine.
 * @return A list of recipes.
 */
suspend fun fetchRecipesByCuisine(cuisineId: String): List<Recipe> {
    return try {
        val userId = supabase.auth.currentSessionOrNull()?.user?.id ?: return emptyList()
        supabase.postgrest["recipes"]
            .select {
                filter {
                    eq("user_id", userId)
                    eq("cuisine_id", cuisineId)
                }
            }
            .decodeList()
    } catch (e: Exception) {
        FirebaseCrashlytics.getInstance()
            .recordException(Exception("Error fetching recipes by cuisine: $e"))
        emptyList()
    }
}

/**
 * Fetches cuisines
 * @return A list of cuisines
 */
suspend fun fetchCuisines(): List<Cuisine> {
    return try {
        supabase.postgrest["cuisines"]
            .select {
                order("name", order = Order.ASCENDING)
            }
            .decodeList()
    } catch (e: Exception) {
        FirebaseCrashlytics.getInstance().recordException(Exception("Error fetching cuisines: $e"))
        emptyList()
    }
}

/**
 * Searches for recipes by title (case-insensitive, partial match).
 * @param query The search text to match against recipe titles.
 * @return A list of matching recipes.
 */
suspend fun searchRecipesByTitle(query: String): List<Recipe> {
    return try {
        val userId = supabase.auth.currentSessionOrNull()?.user?.id ?: return emptyList()
        supabase.postgrest["recipes"]
            .select {
                filter {
                    eq("user_id", userId)
                    ilike("title", "%$query%")
                }
            }
            .decodeList()
    } catch (e: Exception) {
        FirebaseCrashlytics.getInstance().recordException(Exception("Error searching recipes: $e"))
        emptyList()
    }
}