package app.vazovsky.coffe.data.remote.base

sealed class Either<out FailureType, out SuccessType> {

    /** * Represents the left side of [Either] class which by convention is a "Failure". */
    data class Failure<out FailureType>(val error: FailureType) : Either<FailureType, Nothing>()

    data class Success<out SuccessType>(val data: SuccessType) : Either<Nothing, SuccessType>()

    val isSuccess get() = this is Success<SuccessType>

    val isFailure get() = this is Failure<FailureType>

    fun <FailType> failure(error: FailType) = Failure(error)

    fun <SuccessType> success(data: SuccessType) = Success(data)

    fun fold(ifFailure: (FailureType) -> Any = {}, ifSuccess: (SuccessType) -> Any = {}): Any = when (this) {
        is Failure -> ifFailure(error)
        is Success -> ifSuccess(data)
    }
}

fun <A, B, C> ((A) -> B).c(f: (B) -> C): (A) -> C = {
    f(this(it))
}

fun <T, FailureType, SuccessType> Either<FailureType, SuccessType>.flatMap(
    action: (SuccessType) -> Either<FailureType, T>
): Either<FailureType, T> = when (this) {
    is Either.Failure -> Either.Failure(error)
    is Either.Success -> action(data)
}

fun <T, FailureType, SuccessType> Either<FailureType, SuccessType>.map(action: (SuccessType) -> (T)): Either<FailureType, T> =
    this.flatMap(action.c(::success))

fun <FailureType, SuccessType> Either<FailureType, SuccessType>.getOrElse(value: SuccessType): SuccessType =
    when (this) {
        is Either.Failure -> value
        is Either.Success -> data
    }

fun <FailureType, SuccessType> Either<FailureType, SuccessType>.onFailure(
    action: (failure: FailureType) -> Unit
): Either<FailureType, SuccessType> = this.apply {
    if (this is Either.Failure) action(error)
}

fun <Failure, Success> Either<Failure, Success>.onSuccess(
    action: (success: Success) -> Unit
): Either<Failure, Success> = this.apply {
    if (this is Either.Success) action(data)
}