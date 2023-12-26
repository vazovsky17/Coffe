package app.vazovsky.coffe.data.remote

import app.vazovsky.coffe.data.remote.exception.Either
import app.vazovsky.coffe.data.remote.exception.Failure
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
    suspend fun login(@Body body: LoginRequestBody): Either<Failure, TokenResponse>

    @POST("auth/register")
    suspend fun register(@Body body: RegisterRequestBody): Either<Failure, TokenResponse>

    @GET("location/{id}/menu")
    suspend fun getMenu(@Path("id") id: String): Either<Failure, List<ProductResponse>>

    @GET("locations")
    suspend fun getLocations(): Either<Failure, List<LocationResponse>>
}