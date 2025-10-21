package no.milleba.pizzafjoset.model

data class Cart (
    val _id: String? = null,
    val itemTitle: String,
    val category: String,
    val totalPrice: Double?,
    val image: String?,
    val price: Double? = null,
    val priceBig: Double? = null,
    val priceSmall: Double? = null
)