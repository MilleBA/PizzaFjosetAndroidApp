package no.milleba.pizzafjoset.model

import okhttp3.ResponseBody
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

    @GET("api/meals/{_id}")
    suspend fun getMeal(@Path("_id") id: String): Meal

    @POST("api/meals")
    suspend fun createMeal(@Body meal: Meal): ResponseBody

    @DELETE("api/meals/{_id}")
    suspend fun deleteMeal(@Query("_id") id: String): ResponseBody
}