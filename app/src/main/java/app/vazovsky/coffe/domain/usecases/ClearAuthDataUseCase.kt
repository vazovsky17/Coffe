package app.vazovsky.coffe.domain.usecases

import app.vazovsky.coffe.data.preferences.PreferenceStore
import app.vazovsky.coffe.data.remote.exception.Either
import app.vazovsky.coffe.data.remote.exception.Failure
import app.vazovsky.coffe.domain.base.UseCase
import javax.inject.Inject

/** Чистка данных авторизации */
class ClearAuthDataUseCase @Inject constructor(
    private val preferenceStore: PreferenceStore,
) : UseCase<UseCase.None, Unit>() {

    override suspend fun run(params: None): Either<Failure, Unit> {
        preferenceStore.clear()
        return Either.Success(Unit)
    }
}