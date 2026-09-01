package dev.auroralaboratories.myrecipes.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dev.auroralaboratories.myrecipes.dataclasses.Cuisine
import dev.auroralaboratories.myrecipes.dataclasses.Ingredient
import dev.auroralaboratories.myrecipes.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
class CreateRecipeViewModel() : ViewModel() {
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _description = MutableStateFlow<String?>(null)
    val description: StateFlow<String?> = _description.asStateFlow()

    private val _instructions = MutableStateFlow<String?>(null)
    val instructions: StateFlow<String?> = _instructions.asStateFlow()

    private val _servings = MutableStateFlow<Int?>(null)
    val servings: StateFlow<Int?> = _servings.asStateFlow()

    private val _prepTimeMinutes = MutableStateFlow<Int?>(null)
    val prepTimeMinutes: StateFlow<Int?> = _prepTimeMinutes.asStateFlow()

    private val _cookTimeMinutes = MutableStateFlow<Int?>(null)
    val cookTimeMinutes: StateFlow<Int?> = _cookTimeMinutes.asStateFlow()

    private val _ingredients = MutableStateFlow<List<Ingredient>>(emptyList())
    val ingredients: StateFlow<List<Ingredient>> = _ingredients.asStateFlow()

    private val _selectedCuisine = MutableStateFlow<Cuisine?>(null)
    val selectedCuisine: StateFlow<Cuisine?> = _selectedCuisine.asStateFlow()


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val repository = RecipeRepository()

    /**
     * Updates the title of the recipe.
     * @param value The new title of the recipe.
     */
    fun updateTitle(value: String) {
        _title.value = value
    }

    /**
     * Updates the description of the recipe.
     * @param value The new description of the recipe.
     */
    fun updateDescription(value: String) {
        _description.value = value
    }

    /**
     * Updates the instructions for the recipe.
     * @param value The new instructions for the recipe.
     */
    fun updateInstructions(value: String) {
        _instructions.value = value
    }

    /**
     * Updates the number of servings for the recipe.
     * @param value The new number of servings for the recipe.
     */
    fun updateServings(value: Int?) {
        _servings.value = value
    }

    /**
     * Updates the preparation time for the recipe in minutes.
     * @param value The new preparation time for the recipe in minutes.
     */
    fun updatePrepTimeMinutes(value: Int?) {
        _prepTimeMinutes.value = value
    }

    /**
     * Updates the cooking time for the recipe in minutes.
     * @param value The new cooking time for the recipe in minutes.
     */
    fun updateCookTimeMinutes(value: Int?) {
        _cookTimeMinutes.value = value
    }

    /**
     * Adds an ingredient to the list of ingredients for the recipe.
     * @param ingredient The ingredient to add.
     */
    fun addIngredient(ingredient: Ingredient) {
        _ingredients.value = _ingredients.value + ingredient
    }

    /**
     * Removes an ingredient from the list of ingredients for the recipe.
     * @param index The index of the ingredient to remove.
     */
    fun removeIngredientAt(index: Int) {
        _ingredients.value = _ingredients.value.filterIndexed { i, _ -> i != index }
    }

    /**
     * Updates an ingredient in the list of ingredients for the recipe.
     * @param index The index of the ingredient to update.
     */
    fun updateIngredientAt(index: Int, ingredient: Ingredient) {
        _ingredients.value = _ingredients.value.mapIndexed { i, existing ->
            if (i == index) ingredient else existing
        }
    }

    /**
     * Updates the selected cuisine for the recipe.
     * @param cuisine The new selected cuisine for the recipe.
     */
    fun updateCuisine(cuisine: Cuisine?) {
        _selectedCuisine.value = cuisine
    }

    fun saveRecipe() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val recipeId = repository.saveRecipe(
                    title = _title.value,
                    description = _description.value,
                    instructions = _instructions.value,
                    servings = _servings.value,
                    prepTime = _prepTimeMinutes.value,
                    cookTime = _cookTimeMinutes.value,
                    ingredients = _ingredients.value,
                    cuisine = _selectedCuisine.value?.id,
                )
                if (recipeId == null) {
                    _errorMessage.value = "Failed to save recipe"
                }
            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
                _errorMessage.value = "Failed to save recipe"
            } finally {
                _isLoading.value = false
            }
        }
    }

}