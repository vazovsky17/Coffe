package app.vazovsky.coffe.domain.usecases

import app.vazovsky.coffe.data.preferences.PreferenceStore
import app.vazovsky.coffe.data.remote.exception.Either
import app.vazovsky.coffe.data.remote.exception.Failure
import app.vazovsky.coffe.domain.base.UseCase
import app.vazovsky.coffe.domain.model.Token
import javax.inject.Inject

/** Сохранение данных авторизации */
class SaveAuthDataUseCase @Inject constructor(
    private val preferenceStore: PreferenceStore,
) : UseCase<SaveAuthDataUseCase.Params, Token>() {

    override suspend fun run(params: Params): Either<Failure, Token> {
        preferenceStore.saveLoginCredentials(params.token)
        return Either.Success(params.token)
    }

    data class Params(
        val token: Token,
    )
}