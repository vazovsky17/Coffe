package app.vazovsky.coffe.data.remote

import app.vazovsky.coffe.data.remote.request.LoginRequestBody
import app.vazovsky.coffe.data.remote.request.RegisterRequestBody
import app.vazovsky.coffe.data.remote.response.LocationResponse
import app.vazovsky.coffe.data.remote.response.ProductResponse
import app.vazovsky.coffe.data.remote.response.TokenResponse

class MockCoffeApiService : CoffeApiService {

    override suspend fun login(body: LoginRequestBody): TokenResponse {
        return TokenResponse(
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiIsImlzcyI6ImNvZmZlZ" +
                    "SBiYWNrZW5kIiwiaWQiOjQxNiwiZXhwIjoxNzAzNTQwODU1fQ.-Hke-uxWBGGwjNkdN2sKnk0iJNNgFXACt7NsHNkYEkA",
            tokenLifeTime = 3600000,
        )
    }

    override suspend fun register(body: RegisterRequestBody): TokenResponse {
        return TokenResponse(
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiIsImlzcyI6ImNvZmZlZ" +
                    "SBiYWNrZW5kIiwiaWQiOjQxNiwiZXhwIjoxNzAzNTQwODU1fQ.-Hke-uxWBGGwjNkdN2sKnk0iJNNgFXACt7NsHNkYEkA",
            tokenLifeTime = 3600000,
        )
    }

    override suspend fun getMenu(id: String): List<ProductResponse> {
        return listOf(
            ProductResponse(
                id = 1,
                name = "Эспрессо",
                imageURL = null,
                price = 200,
            ),
            ProductResponse(
                id = 2,
                name = "Капучино",
                imageURL = null,
                price = 200,
            ),
            ProductResponse(
                id = 2,
                name = "Горячий шоколад",
                imageURL = null,
                price = 200,
            ),
            ProductResponse(
                id = 2,
                name = "Латте",
                imageURL = null,
                price = 200,
            ),
        )
    }

    override suspend fun getLocations(): List<LocationResponse> {
        return listOf()
    }
}