package dev.auroralaboratories.myrecipes.repository

import dev.auroralaboratories.myrecipes.databasefunctions.fetchAllRecipes
import dev.auroralaboratories.myrecipes.dataclasses.Recipe

class RecipeRepositry {


    suspend fun getAllRecipes(): List<Recipe> {
            return fetchAllRecipes()
    }
}