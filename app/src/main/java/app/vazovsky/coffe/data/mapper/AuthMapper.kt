package app.vazovsky.coffe.data.mapper

import app.vazovsky.coffe.data.remote.response.TokenResponse
import app.vazovsky.coffe.domain.model.Token
import app.vazovsky.coffe.extensions.orDefault
import javax.inject.Inject

class AuthMapper @Inject constructor() {

    fun fromApiToModel(apiModel: TokenResponse?): Token {
        return Token(
            token = apiModel?.token.orDefault(),
            tokenLifeTime = apiModel?.tokenLifeTime.orDefault(),
        )
    }
}