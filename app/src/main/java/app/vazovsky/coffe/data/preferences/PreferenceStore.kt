package app.vazovsky.coffe.data.preferences

import app.vazovsky.coffe.domain.model.Token
import com.orhanobut.hawk.Hawk
import java.time.LocalDateTime
import javax.inject.Inject

class PreferenceStore @Inject constructor() {
    companion object {
        const val DEFAULT_PREFERENCE = "COFFE_APP_PREF"

        const val IS_LOGGED_IN = "is_logged_in"
        const val KEY_TOKEN = "token"
        const val LAST_AUTH_MILLIS = "last_auth_millis"
    }

    var userLoggedIn: Boolean?
        get() = Hawk.get(IS_LOGGED_IN, false)
        set(value) {
            Hawk.put(IS_LOGGED_IN, value)
        }

    var tokenInfo: Token?
        get() = Hawk.get(KEY_TOKEN)
        set(value) {
            Hawk.put(KEY_TOKEN, value)
        }

    var lastAuthMillis: Long?
        get() = Hawk.get(LAST_AUTH_MILLIS)
        set(value) {
            Hawk.put(LAST_AUTH_MILLIS, value)
        }

    fun saveLoginCredentials(token: Token) {
        with(tokenInfo) {
            lastAuthMillis = System.currentTimeMillis()
            tokenInfo = token
            userLoggedIn = true
        }
    }

    fun clear() {
        Hawk.deleteAll()
    }
}