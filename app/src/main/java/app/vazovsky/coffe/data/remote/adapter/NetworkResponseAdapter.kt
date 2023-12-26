package app.vazovsky.coffe.data.remote.adapter

import app.vazovsky.coffe.data.remote.exception.Either
import app.vazovsky.coffe.data.remote.exception.Failure
import java.lang.reflect.Type
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter

class NetworkResponseAdapter<S : Any>(
    private val successType: Type,
    private val errorBodyConverter: Converter<ResponseBody, Error>
) : CallAdapter<S, Call<Either<Failure, S>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<S>): Call<Either<Failure, S>> {
        return NetworkResponseCall(call, errorBodyConverter)
    }
}