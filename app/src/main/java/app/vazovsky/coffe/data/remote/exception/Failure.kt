package app.vazovsky.coffe.data.remote.exception

sealed class Failure(open val message: String) {
    class BadRequest(override val message: String) : Failure(message)
    class Unauthorized(override val message: String) : Failure(message)
    class UnknownError(override val message: String = "unknownError") : Failure(message)
}