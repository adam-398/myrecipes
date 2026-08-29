package dev.auroralaboratories.myrecipes.dataclasses

import kotlinx.serialization.Serializable

/**
 * Data class representing a cuisine.
 * @property id The unique identifier for the cuisine.
 * @property name The name of the cuisine.
 */
@Serializable
data class Cuisine(
    val id: String? = null,
    val name: String
)