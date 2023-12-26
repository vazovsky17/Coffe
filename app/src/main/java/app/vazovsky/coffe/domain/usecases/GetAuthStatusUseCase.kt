package app.vazovsky.coffe.domain.usecases

import app.vazovsky.coffe.data.preferences.PreferenceStore
import app.vazovsky.coffe.data.remote.exception.Either
import app.vazovsky.coffe.data.remote.exception.Failure
import app.vazovsky.coffe.domain.base.UseCase
import app.vazovsky.coffe.extensions.orDefault
import javax.inject.Inject

/** Получение данных об авторизации */
class GetAuthStatusUseCase @Inject constructor(
    private val preferenceStore: PreferenceStore,
) : UseCase<UseCase.None, Boolean>() {

    override suspend fun run(params: None): Either<Failure, Boolean> {
        val isLoggedIn = preferenceStore.userLoggedIn == true && preferenceStore.tokenInfo != null
        val timeFromLastLogin = System.currentTimeMillis() - preferenceStore.tokenInfo?.tokenLifeTime.orDefault()
        val isLoggedTimeIn = timeFromLastLogin > preferenceStore.lastAuthMillis.orDefault()

        if (!isLoggedTimeIn) {
            preferenceStore.clear()
        }

        return Either.Success(isLoggedIn && isLoggedTimeIn)
    }
}