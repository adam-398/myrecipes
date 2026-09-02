package dev.auroralaboratories.myrecipes.recipeframes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import dev.auroralaboratories.myrecipes.viewmodels.CreateRecipeViewModel

/**
 * Composable function that displays the create recipe screen.
 * @param viewModel The CreateRecipeViewModel to use for data management.
 * @param navController The NavController to use for navigation.
 */
@Composable
fun CreateRecipe(
    viewModel: CreateRecipeViewModel,
    navController: NavController
    ) {
    val title by viewModel.title.collectAsState()
    val description by viewModel.description.collectAsState()
    val instructions by viewModel.instructions.collectAsState()
    val servings by viewModel.servings.collectAsState()
    val prepTimeMinutes by viewModel.prepTimeMinutes.collectAsState()
    val cookTimeMinutes by viewModel.cookTimeMinutes.collectAsState()
    val availableCuisines by viewModel.availableCuisines.collectAsState()
    val selectedCuisine by viewModel.selectedCuisine.collectAsState()
}