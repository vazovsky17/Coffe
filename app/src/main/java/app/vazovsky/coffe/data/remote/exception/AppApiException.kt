package app.vazovsky.coffe.data.remote.exception

object AppApiException {
    fun getFailure(errorCode: Long): Failure {
        return when (errorCode) {
            400L -> BadRequest("Неверный запрос")
            401L -> Unauthorized("Невалидный токен")
            else -> UnknownError()
        }
    }

    class BadRequest(val message: String) : Failure.FeatureFailure()
    class Unauthorized(val message: String) : Failure.FeatureFailure()
    class UnknownError(message: String? = "unknownError") : Failure.FeatureFailure()
}