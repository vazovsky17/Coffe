package app.vazovsky.coffe.data.remote

import app.vazovsky.coffe.data.remote.request.LoginRequestBody
import app.vazovsky.coffe.data.remote.request.RegisterRequestBody
import app.vazovsky.coffe.data.remote.response.LocationResponse
import app.vazovsky.coffe.data.remote.response.ProductResponse
import app.vazovsky.coffe.data.remote.response.TokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CoffeApiService {

    @POST("auth/login")
    suspend fun login(@Body body: LoginRequestBody): TokenResponse

    @POST("auth/register")
    suspend fun register(@Body body: RegisterRequestBody): TokenResponse

    @GET("location/{id}/menu")
    suspend fun getMenu(@Path("id") id: String): List<ProductResponse>

    @GET("locations")
    suspend fun getLocations(): List<LocationResponse>
}