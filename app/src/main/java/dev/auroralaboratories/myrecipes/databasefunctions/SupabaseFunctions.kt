package dev.auroralaboratories.myrecipes.databasefunctions

import com.google.firebase.crashlytics.FirebaseCrashlytics
import dev.auroralaboratories.myrecipes.databasefunctions.SupabaseClient.supabase
import dev.auroralaboratories.myrecipes.dataclasses.Recipe
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest

/**
 * Adds a new recipe to the database.
 * @param title The title of the recipe.
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
    cookTime: Int? = null
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
            cook_time_minutes = cookTime
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