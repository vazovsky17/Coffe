package app.vazovsky.coffe.data.repository

import app.vazovsky.coffe.data.remote.base.Failure
import app.vazovsky.coffe.data.remote.base.Either
import app.vazovsky.coffe.domain.model.Token

interface AuthRepository {

    suspend fun login(login: String, password: String): Either<Failure, Token>

    suspend fun register(login: String, password: String): Either<Failure, Token>
}