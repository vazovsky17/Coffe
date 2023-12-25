package app.vazovsky.coffe.domain.usecases

import app.vazovsky.coffe.data.repository.AuthRepository
import app.vazovsky.coffe.domain.base.UseCaseUnary
import app.vazovsky.coffe.domain.model.Token
import javax.inject.Inject

/** Регистрация пользователя */
class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) : UseCaseUnary<RegisterUseCase.Params, Token>() {

    override suspend fun execute(params: Params): Token {
        return authRepository.register(
            login = params.login,
            password = params.password,
        )
    }

    data class Params(
        val login: String,
        val password: String,
    )
}