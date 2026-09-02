package dev.auroralaboratories.myrecipes.recipeframes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.auroralaboratories.myrecipes.uicomponents.AuroraInputField
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
    //val availableCuisines by viewModel.availableCuisines.collectAsState()
    //val selectedCuisine by viewModel.selectedCuisine.collectAsState()

    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = "Create Recipe",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            textAlign = TextAlign.Center
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AuroraInputField(
                value = title,
                onValueChange = { viewModel.updateTitle(it) },
                label = "Title",
                modifier = Modifier.fillMaxWidth()
            )
            AuroraInputField(
                value = description ?: "",
                onValueChange = { viewModel.updateDescription(it) },
                label = "Description",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun CreateRecipePreview() {
    CreateRecipe(viewModel = CreateRecipeViewModel(), navController = rememberNavController())
}