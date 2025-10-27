package no.milleba.pizzafjoset.model

data class CartItem (
    val meal: Meal,
    val price: Double? = null,
    val quantity: Int
)