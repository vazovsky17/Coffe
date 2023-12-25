package app.vazovsky.coffe.domain.usecases

import app.vazovsky.coffe.data.repository.AuthRepository
import app.vazovsky.coffe.domain.base.UseCaseUnary
import app.vazovsky.coffe.domain.model.Token
import javax.inject.Inject

/** Авторизация пользователя */
class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) : UseCaseUnary<LoginUseCase.Params, Token>() {

    override suspend fun execute(params: Params): Token {
        return authRepository.login(
            login = params.login,
            password = params.password,
        )
    }

    data class Params(
        val login: String,
        val password: String,
    )
}