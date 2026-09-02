package dev.auroralaboratories.myrecipes.recipeframes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import dev.auroralaboratories.myrecipes.IngredientRow
import dev.auroralaboratories.myrecipes.dataclasses.Ingredient
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
    val ingredients by viewModel.ingredients.collectAsState()
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
                .weight(1f)
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

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ingredients.forEachIndexed { index, ingredient ->
                    IngredientRow(
                        ingredient = ingredient,
                        onNameChange = { newName ->
                            viewModel.updateIngredientAt(index, ingredient.copy(name = newName))
                        },
                        onQuantityChange = { newQuantity ->
                            viewModel.updateIngredientAt(
                                index,
                                ingredient.copy(quantity = newQuantity.toDoubleOrNull())
                            )
                        },
                        onUnitChange = { newUnit ->
                            viewModel.updateIngredientAt(index, ingredient.copy(unit = newUnit))
                        },
                        onRemove = { viewModel.removeIngredientAt(index) }
                    )
                }
                Button(onClick = { viewModel.addIngredient(Ingredient(name = "")) }) {
                    Text("Add Ingredient")
                }
            }

            AuroraInputField(
                value = instructions ?: "",
                onValueChange = { viewModel.updateInstructions(it) },
                label = "Instructions",
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                minLines = 7
            )
        }
    }
}

@Preview
@Composable
fun CreateRecipePreview() {
    CreateRecipe(viewModel = CreateRecipeViewModel(), navController = rememberNavController())
}