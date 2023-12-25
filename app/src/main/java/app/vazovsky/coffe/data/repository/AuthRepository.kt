package app.vazovsky.coffe.data.repository

import app.vazovsky.coffe.domain.model.Token

interface AuthRepository {

    suspend fun login(login: String, password: String): Token

    suspend fun register(login: String, password: String): Token
}