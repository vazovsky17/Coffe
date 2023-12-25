package app.vazovsky.coffe.data.remote.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class LoginRequestBody(
    @SerializedName("login") val login: String,
    @SerializedName("password") val password: String,
) : Parcelable