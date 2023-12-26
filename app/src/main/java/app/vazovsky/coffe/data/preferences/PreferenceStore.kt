package app.vazovsky.coffe.data.preferences

import app.vazovsky.coffe.domain.model.Token
import com.orhanobut.hawk.Hawk
import javax.inject.Inject

class PreferenceStore @Inject constructor() {
    companion object {
        const val DEFAULT_PREFERENCE = "COFFE_APP_PREF"

        const val IS_LOGGED_IN = "is_logged_in"
        const val KEY_TOKEN = "token"
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

    fun saveLoginCredentials(token: Token) {
        with(tokenInfo) {
            tokenInfo = token
            userLoggedIn = true
        }
    }

    fun clear() {
        Hawk.deleteAll()
    }
}