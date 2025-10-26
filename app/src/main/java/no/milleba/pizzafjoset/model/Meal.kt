package no.milleba.pizzafjoset.model

import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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
    suspend fun deleteMeal(@Path("_id") id: String): ResponseBody
}

interface MealsRepository {
    suspend fun getMeals(): List<Meal>
}

class MealsRepositoryImpl(
    private val api: MealApi
) : MealsRepository {
    override suspend fun getMeals(): List<Meal> = api.getMeals().meals
}
