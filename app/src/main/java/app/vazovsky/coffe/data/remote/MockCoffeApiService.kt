package app.vazovsky.coffe.data.remote

import app.vazovsky.coffe.data.remote.exception.Either
import app.vazovsky.coffe.data.remote.exception.Failure
import app.vazovsky.coffe.data.remote.request.LoginRequestBody
import app.vazovsky.coffe.data.remote.request.RegisterRequestBody
import app.vazovsky.coffe.data.remote.response.LocationResponse
import app.vazovsky.coffe.data.remote.response.ProductResponse
import app.vazovsky.coffe.data.remote.response.TokenResponse

class MockCoffeApiService : CoffeApiService {

    override suspend fun login(body: LoginRequestBody): Either<Failure, TokenResponse> {
        return Either.Success(
            TokenResponse(
                token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiIsImlzcyI6ImNvZmZlZ" +
                        "SBiYWNrZW5kIiwiaWQiOjQxNiwiZXhwIjoxNzAzNTQwODU1fQ.-Hke-uxWBGGwjNkdN2sKnk0iJNNgFXACt7NsHNkYEkA",
                tokenLifeTime = 3600000,
            ),
        )
    }

    override suspend fun register(body: RegisterRequestBody): Either<Failure, TokenResponse> {
        return Either.Success(
            TokenResponse(
                token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiIsImlzcyI6ImNvZmZlZ" +
                        "SBiYWNrZW5kIiwiaWQiOjQxNiwiZXhwIjoxNzAzNTQwODU1fQ.-Hke-uxWBGGwjNkdN2sKnk0iJNNgFXACt7NsHNkYEkA",
                tokenLifeTime = 3600000,
            ),
        )
    }

    override suspend fun getMenu(id: Int): Either<Failure, List<ProductResponse>> {
        return Either.Success(
            listOf(
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
            ),
        )
    }

    override suspend fun getLocations(): Either<Failure, List<LocationResponse>> {
        return Either.Success(
            listOf(
                LocationResponse(
                    id = 1,
                    name = "BEDOEV COFFEE",
                    point = null,
                ),
                LocationResponse(
                    id = 2,
                    name = "Coffee Like",
                    point = null,
                ),
                LocationResponse(
                    id = 3,
                    name = "EM&DI Coffee and Snacks",
                    point = null,
                ),
                LocationResponse(
                    id = 4,
                    name = "Коффе есть",
                    point = null,
                ),
                LocationResponse(
                    id = 5,
                    name = "BEDOEV COFFEE 2",
                    point = null,
                ),
            ),
        )
    }
}