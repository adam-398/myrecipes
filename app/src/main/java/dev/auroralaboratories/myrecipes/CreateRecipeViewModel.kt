package dev.auroralaboratories.myrecipes

import androidx.lifecycle.ViewModel
import dev.auroralaboratories.myrecipes.dataclasses.Cuisine
import dev.auroralaboratories.myrecipes.dataclasses.Ingredient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel class for creating a recipe.
 * @property _title The title of the recipe.
 * @property title The title of the recipe.
 * @property _description The description of the recipe.
 * @property description The description of the recipe.
 * @property _instructions The instructions for the recipe.
 * @property instructions The instructions for the recipe.
 * @property _servings The number of servings for the recipe.
 * @property servings The number of servings for the recipe.
 * @property _prepTimeMinutes The preparation time for the recipe in minutes.
 * @property prepTimeMinutes The preparation time for the recipe in minutes.
 * @property _cookTimeMinutes The cooking time for the recipe in minutes.
 * @property cookTimeMinutes The cooking time for the recipe in minutes.
 * @property _ingredients The list of ingredients for the recipe.
 * @property ingredients The list of ingredients for the recipe.
 */
class CreateRecipeViewModel(): ViewModel() {
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _description = MutableStateFlow<String?>(null)
    val description: StateFlow<String?> = _description

    private val _instructions = MutableStateFlow<String?>(null)
    val instructions: StateFlow<String?> = _instructions

    private val _servings = MutableStateFlow<Int?>(null)
    val servings: StateFlow<Int?> = _servings

    private val _prepTimeMinutes = MutableStateFlow<Int?>(null)
    val prepTimeMinutes: StateFlow<Int?> = _prepTimeMinutes

    private val _cookTimeMinutes = MutableStateFlow<Int?>(null)
    val cookTimeMinutes: StateFlow<Int?> = _cookTimeMinutes

    private val _ingredients = MutableStateFlow<List<Ingredient>>(emptyList())
    val ingredients: StateFlow<List<Ingredient>> = _ingredients

    private val _selectedCuisine = MutableStateFlow<Cuisine?>(null)
    val selectedCuisine: StateFlow<Cuisine?> = _selectedCuisine

}