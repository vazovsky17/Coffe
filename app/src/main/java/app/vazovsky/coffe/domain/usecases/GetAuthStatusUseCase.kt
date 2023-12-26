package app.vazovsky.coffe.domain.usecases

import app.vazovsky.coffe.data.preferences.PreferenceStore
import app.vazovsky.coffe.data.remote.exception.Either
import app.vazovsky.coffe.data.remote.exception.Failure
import app.vazovsky.coffe.domain.base.UseCase
import javax.inject.Inject

/** Получение данных об авторизации */
class GetAuthStatusUseCase @Inject constructor(
    private val preferenceStore: PreferenceStore,
) : UseCase<UseCase.None, Boolean>() {

    override suspend fun run(params: None): Either<Failure, Boolean> {
        return Either.Success(
            preferenceStore.userLoggedIn == true && preferenceStore.tokenInfo != null
        )
    }
}