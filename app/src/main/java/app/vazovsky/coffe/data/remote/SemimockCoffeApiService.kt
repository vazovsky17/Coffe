package app.vazovsky.coffe.data.remote

import app.vazovsky.coffe.data.remote.base.Failure
import app.vazovsky.coffe.data.remote.base.Either
import app.vazovsky.coffe.data.remote.request.LoginRequestBody
import app.vazovsky.coffe.data.remote.request.RegisterRequestBody
import app.vazovsky.coffe.data.remote.response.LocationResponse
import app.vazovsky.coffe.data.remote.response.ProductResponse
import app.vazovsky.coffe.data.remote.response.TokenResponse

class SemimockCoffeApiService(
    private val apiService: CoffeApiService,
    private val mockApiService: MockCoffeApiService,
) : CoffeApiService {

    override suspend fun login(body: LoginRequestBody): Either<Failure, TokenResponse> {
        return apiService.login(body)
    }

    override suspend fun register(body: RegisterRequestBody): Either<Failure, TokenResponse> {
        return apiService.register(body)
    }

    override suspend fun getMenu(id: String): Either<Failure, List<ProductResponse>> {
        return mockApiService.getMenu(id)
    }

    override suspend fun getLocations(): Either<Failure, List<LocationResponse>> {
        return mockApiService.getLocations()
    }
}