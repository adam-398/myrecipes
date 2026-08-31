package dev.auroralaboratories.myrecipes.repository

import dev.auroralaboratories.myrecipes.databasefunctions.deleteRecipeById
import dev.auroralaboratories.myrecipes.databasefunctions.fetchAllRecipes
import dev.auroralaboratories.myrecipes.databasefunctions.fetchCuisines
import dev.auroralaboratories.myrecipes.databasefunctions.fetchRecipeById
import dev.auroralaboratories.myrecipes.databasefunctions.fetchRecipesByCuisine
import dev.auroralaboratories.myrecipes.databasefunctions.searchRecipesByTitle
import dev.auroralaboratories.myrecipes.databasefunctions.updateRecipeById
import dev.auroralaboratories.myrecipes.dataclasses.Cuisine
import dev.auroralaboratories.myrecipes.dataclasses.Recipe

/**
 * Repository class for recipes.
 */
class RecipeRepository {

    /**
     * Fetches all recipes from the database.
     * @return A list of recipes.
     */
    suspend fun getAllRecipes(): List<Recipe> {
            return fetchAllRecipes()
    }

    /**
     * Fetches a recipe by its ID.
     * @param id The ID of the recipe.
     */
    suspend fun getRecipeById(id: String): Recipe? {
        return fetchRecipeById(id)
    }

    /**
     * Deletes a recipe by its ID.
     * @param id The ID of the recipe.
     * @return True if the deletion was successful, false otherwise.
     */
    suspend fun removeRecipeById(id: String): Boolean {
        return deleteRecipeById(id)
    }

    /**
     * Edits a recipe by its ID.
     * @param id The ID of the recipe.
     * @param recipe The updated recipe object.
     * @return True if the edit was successful, false otherwise.
     */
    suspend fun editRecipeById(id: String, recipe: Recipe): Boolean {
        return updateRecipeById(id, recipe)
    }

    /**
     * Fetches recipes by cuisine.
     * @param cuisineId The ID of the cuisine.
     * @return A list of recipes.
     */
    suspend fun getRecipesByCuisine(cuisineId: String): List<Recipe> {
        return fetchRecipesByCuisine(cuisineId)
    }

    /**
     * Fetches cuisines.
     * @return A list of cuisines.
     */
    suspend fun getCuisines(): List<Cuisine> {
        return fetchCuisines()
    }

    /**
     * Searches for recipes by title (case-insensitive, partial match).
     * @param query The search text to match against recipe titles.
     */
    suspend fun lookupRecipeByTitle(query: String): List<Recipe> {
        return searchRecipesByTitle(query)
    }
}