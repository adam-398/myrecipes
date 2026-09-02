package dev.auroralaboratories.myrecipes

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.auroralaboratories.myrecipes.dataclasses.Ingredient
import dev.auroralaboratories.myrecipes.uicomponents.AuroraDropdownField
import dev.auroralaboratories.myrecipes.uicomponents.AuroraInputField

@Composable
fun IngredientRow(
    ingredient: Ingredient,
    onNameChange: (String) -> Unit,
    onQuantityChange: (String) -> Unit,
    onUnitChange: (String) -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AuroraInputField(
            value = ingredient.name,
            onValueChange = onNameChange,
            label = "Ingredient",
            modifier = Modifier.weight(1f)
        )
        AuroraInputField(
            value = ingredient.quantity?.toString() ?: "",
            onValueChange = onQuantityChange,
            label = "Quantity",
            modifier = Modifier.weight(0.5f)
        )
        AuroraDropdownField(
            selectedValue = ingredient.unit ?: "",
            options = listOf("cups", "tbsp", "tsp", "g", "kg", "ml", "l", "oz", "lb"),
            onValueSelected = onUnitChange,
            label = "Unit",
            modifier = Modifier.weight(0.6f)
        )
        IconButton(onClick = { onRemove() }) {
            Icon(Icons.Default.Close, contentDescription = "Remove Ingredient")
        }
    }
}

@Preview
@Composable
fun IngredientRowPreview() {
    IngredientRow(
        ingredient = Ingredient(name = "Flour", quantity = 2.0, unit = "cups"),
        onNameChange = {}, onQuantityChange = {}, onUnitChange = {}, onRemove = {}
    )
}