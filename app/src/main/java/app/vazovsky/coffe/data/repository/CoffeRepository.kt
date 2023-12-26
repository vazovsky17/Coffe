package app.vazovsky.coffe.data.repository

import app.vazovsky.coffe.data.remote.exception.Either
import app.vazovsky.coffe.data.remote.exception.Failure
import app.vazovsky.coffe.domain.model.CoffeeShop
import app.vazovsky.coffe.domain.model.Product

interface CoffeRepository {

    suspend fun getMenu(id: Int): Either<Failure, List<Product>>

    suspend fun getLocations(): Either<Failure, List<CoffeeShop>>
}