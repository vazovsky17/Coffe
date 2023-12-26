package app.vazovsky.coffe.data.repository

import app.vazovsky.coffe.data.remote.exception.Failure
import app.vazovsky.coffe.data.mapper.AuthMapper
import app.vazovsky.coffe.data.remote.exception.Either
import app.vazovsky.coffe.data.remote.exception.map
import app.vazovsky.coffe.data.remote.CoffeApiService
import app.vazovsky.coffe.data.remote.request.LoginRequestBody
import app.vazovsky.coffe.data.remote.request.RegisterRequestBody
import app.vazovsky.coffe.domain.model.Token
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: CoffeApiService,
    private val authMapper: AuthMapper,
) : AuthRepository {

    override suspend fun login(login: String, password: String): Either<Failure, Token> {
        return apiService.login(
            LoginRequestBody(
                login = login,
                password = password,
            ),
        ).map { token ->
            authMapper.fromApiToModel(token)
        }
    }

    override suspend fun register(login: String, password: String): Either<Failure, Token> {
        return apiService.register(
            RegisterRequestBody(
                login = login,
                password = password,
            ),
        ).map { token ->
            authMapper.fromApiToModel(token)
        }
    }
}