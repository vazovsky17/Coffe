package app.vazovsky.coffe.data.remote.base

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import retrofit2.CallAdapter
import retrofit2.Retrofit

class CallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        if (returnType !is ParameterizedType) return null
        val responseType = getParameterUpperBound(0, returnType)
        if (responseType !is ParameterizedType) return null
        if (getRawType(responseType) != Either::class.java) {
            return null
        }
        val successBodyType = getParameterUpperBound(1, responseType)
        val errorBodyType = getParameterUpperBound(0, responseType)
        if (errorBodyType != Failure::class.java) {
            return null
        }
        val errorBodyConverter =
            retrofit.nextResponseBodyConverter<Error>(null, Error::class.java, annotations)

        return NetworkResponseAdapter<Any>(successBodyType, errorBodyConverter)
    }
}