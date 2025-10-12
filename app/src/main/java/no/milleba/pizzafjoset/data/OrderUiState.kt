package no.milleba.pizzafjoset.data

import no.milleba.pizzafjoset.model.Meal

data class OrderUiState(
    val meals: List<Meal> = emptyList(),
    val quantity: Int = 0,
    val date: String = "",
    val price: Double = 0.0,
    val pickupOptions: List<String> = listOf()
) {

}
