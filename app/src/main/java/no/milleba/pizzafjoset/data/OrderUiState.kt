package no.milleba.pizzafjoset.data

data class OrderUiState(
    val items: Map<String, Int> = emptyMap(),
    val totalCount: Int = 0,
    val totalPrice: Double = 0.0,
    val selectedDate: String? = null,
    val pickupOptions: List<String> = emptyList()
) {

}
