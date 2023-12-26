package app.vazovsky.coffe.data.remote.exception

sealed class Failure {
    fun getFailure(errorCode: Long): Failure {
        return when (errorCode) {
            400L -> BadRequest("Неверный запрос")
            401L -> Unauthorized("Невалидный токен")
            else -> UnknownError()
        }
    }

    class BadRequest(val message: String) : Failure()
    class Unauthorized(val message: String) : Failure()
    class UnknownError(message: String? = "unknownError") : Failure()
}