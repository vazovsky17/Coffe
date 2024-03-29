package app.vazovsky.coffe.data.remote.adapter

import app.vazovsky.coffe.data.remote.exception.Either
import app.vazovsky.coffe.data.remote.exception.Failure
import app.vazovsky.coffe.extensions.orDefault
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response

internal class NetworkResponseCall<S : Any>(
    private val delegate: Call<S>,
    private val errorConverter: Converter<ResponseBody, Error>
) : Call<Either<Failure, S>> {

    override fun enqueue(callback: Callback<Either<Failure, S>>) {
        return delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()

                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(Either.Success(body))
                        )
                    } else {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(Either.Failure(Failure.UnknownError()))
                        )
                    }
                } else {
                    if (response.code() == 401) {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(Either.Failure(Failure.Unauthorized(response.message())))
                        )
                    } else if (response.code() == 400) {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(Either.Failure(Failure.BadRequest(response.message())))
                        )
                    } else {
                        val errorFailure: Failure = Failure.UnknownError()
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(
                                Either.Failure(errorFailure)
                            )
                        )
                    }
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                val leftFailure = Failure.UnknownError(throwable.message.orDefault())
                callback.onResponse(
                    this@NetworkResponseCall,
                    Response.success(Either.Failure(leftFailure))
                )
            }
        })
    }

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = NetworkResponseCall(delegate.clone(), errorConverter)

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<Either<Failure, S>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}