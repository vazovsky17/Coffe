package app.vazovsky.coffe.data.model.base

import com.google.gson.annotations.SerializedName

/**
 * Ошибка запроса
 * @param error Данные об ошибке запроса
 */
data class ApiErrorResponse(
    @SerializedName("error") val error: Data?,
) {

    /**
     * Данные об ошибке запроса
     *
     * @param code Код ошибки
     * @param message Описание ошибки
     */
    data class Data(
        @SerializedName("code") val code: String?,
        @SerializedName("message") val message: String?,
    )
}