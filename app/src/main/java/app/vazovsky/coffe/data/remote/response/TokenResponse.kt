package app.vazovsky.coffe.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class TokenResponse(
    @SerializedName("token") val token: String?,
    @SerializedName("tokenLifetime") val tokenLifeTime: Long?,
) : Parcelable