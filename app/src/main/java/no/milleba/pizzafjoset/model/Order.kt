package no.milleba.pizzafjoset.model

import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

data class Order(
    val _id: String? = null,
    val cart: List<Meal>

)

data class OrderResponse(val orders: List<Order>)

interface OrderApi {
    @GET("api/orders")
    suspend fun getOrders(): OrderResponse

//
//    @GET("api/orders/{_id}")
//    suspend fun getOrder(@Query("_id") id: String): Order

    @POST("api/orders")
    suspend fun createOrder(@Body order: Order): ResponseBody

    @PUT("api/orders/{_id}")
    suspend fun updateOrder(@Query("_id") id: String, @Body order: Order): ResponseBody

    @DELETE("api/orders/{_id}")
    suspend fun deleteOrder(@Query("_id") id: String): ResponseBody

}