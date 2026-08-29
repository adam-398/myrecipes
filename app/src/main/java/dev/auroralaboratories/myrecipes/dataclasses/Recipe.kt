package dev.auroralaboratories.myrecipes.dataclasses

import kotlinx.serialization.Serializable

/**
 * Data class representing a recipe.
 * @property id The unique identifier for the recipe.
 * @property user_id The unique identifier for the user who created the recipe.
 * @property cuisine_id The unique identifier for the cuisine of the recipe.
 * @property title The title of the recipe.
 * @property description The description of the recipe.
 * @property instructions The instructions for the recipe.
 * @property servings The number of servings for the recipe.
 * @property prep_time_minutes The preparation time for the recipe in minutes.
 * @property cook_time_minutes The cooking time for the recipe in minutes.
 * @property created_at The date and time when the recipe was created.
 */
@Serializable
data class Recipe(
    val id: String? = null,
    val user_id: String,
    val cuisine_id: String? = null,
    val title: String,
    val description: String? = null,
    val instructions: String? = null,
    val servings: Int? = null,
    val prep_time_minutes: Int? = null,
    val cook_time_minutes: Int? = null,
    val created_at: String? = null,
)
