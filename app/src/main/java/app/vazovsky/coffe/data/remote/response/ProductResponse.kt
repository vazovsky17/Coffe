package app.vazovsky.coffe.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class ProductResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("imageURL") val imageURL: String?,
    @SerializedName("price") val price: Int?,
) : Parcelable