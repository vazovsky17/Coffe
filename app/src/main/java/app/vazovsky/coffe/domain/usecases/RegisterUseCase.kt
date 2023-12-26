package app.vazovsky.coffe.domain.usecases

import app.vazovsky.coffe.data.remote.exception.Failure
import app.vazovsky.coffe.data.remote.exception.Either
import app.vazovsky.coffe.data.repository.AuthRepository
import app.vazovsky.coffe.domain.base.UseCase
import app.vazovsky.coffe.domain.model.Token
import javax.inject.Inject

/** Регистрация пользователя */
class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) : UseCase<RegisterUseCase.Params, Token>() {

    override suspend fun run(params: Params): Either<Failure, Token?> {
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