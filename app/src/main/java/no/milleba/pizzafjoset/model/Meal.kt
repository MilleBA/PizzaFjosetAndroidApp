package no.milleba.pizzafjoset.model

import retrofit2.http.*

data class Meal(
    val _id: String? = null,
    val title: String,
    val category: String,
    val description: String?,
    val image: String?,
    val price: Double? = null,
    val priceBig: Double? = null,
    val priceSmall: Double? = null
)

data class MealResponse(val meals: List<Meal>)

interface MealApi {
    @GET("api/meals")
    suspend fun getMeals(): MealResponse

    @POST("api/meals")
    suspend fun createMeal(@Body meal: Meal): Map<String, String>

    @DELETE("api/meals")
    suspend fun deleteMeal(@Query("id") id: String): Map<String, String>
}