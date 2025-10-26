package no.milleba.pizzafjoset.data

import no.milleba.pizzafjoset.model.Meal
import no.milleba.pizzafjoset.ui.components.Category

data class MealsUiState(
    val meals: List<Meal> = emptyList(),
    val loading: Boolean = true,
    val error: String? = null,
    val selectedCategory: Category? = Category.PIZZA
)
