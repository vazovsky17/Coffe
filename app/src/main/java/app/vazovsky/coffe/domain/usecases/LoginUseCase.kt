package app.vazovsky.coffe.domain.usecases

import app.vazovsky.coffe.data.remote.base.Failure
import app.vazovsky.coffe.data.remote.base.Either
import app.vazovsky.coffe.data.repository.AuthRepository
import app.vazovsky.coffe.domain.base.UseCase
import app.vazovsky.coffe.domain.model.Token
import javax.inject.Inject

/** Авторизация пользователя */
class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) : UseCase<LoginUseCase.Params, Token>() {

    override suspend fun run(params: Params): Either<Failure, Token?> {
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