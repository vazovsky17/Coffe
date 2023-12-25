package app.vazovsky.coffe.data.repository

import app.vazovsky.coffe.data.mapper.AuthMapper
import app.vazovsky.coffe.data.remote.CoffeApiService
import app.vazovsky.coffe.data.remote.request.LoginRequestBody
import app.vazovsky.coffe.data.remote.request.RegisterRequestBody
import app.vazovsky.coffe.domain.model.Token
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: CoffeApiService,
    private val authMapper: AuthMapper,
) : AuthRepository {

    override suspend fun login(login: String, password: String): Token {
        return authMapper.fromApiToModel(
            apiService.login(
                LoginRequestBody(
                    login = login,
                    password = password,
                ),
            ),
        )
    }

    override suspend fun register(login: String, password: String): Token {
        return authMapper.fromApiToModel(
            apiService.register(
                RegisterRequestBody(
                    login = login,
                    password = password,
                ),
            ),
        )
    }
}