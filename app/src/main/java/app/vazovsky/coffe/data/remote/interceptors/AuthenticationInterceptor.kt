package app.vazovsky.coffe.data.remote.interceptors

import app.vazovsky.coffe.data.preferences.PreferenceStore
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthenticationInterceptor @Inject constructor(
    private val preferenceStore: PreferenceStore
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        return if (preferenceStore.userLoggedIn == true && !request.url.toString().contains("refresh-token")) {
            val req = request.newBuilder().header("Authorization", "Bearer ${preferenceStore.tokenInfo?.token}").build()
            chain.proceed(req)
        } else {
            chain.proceed(request)
        }
    }
}