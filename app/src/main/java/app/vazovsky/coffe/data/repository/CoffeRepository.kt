package app.vazovsky.coffe.data.repository

import app.vazovsky.coffe.domain.model.Location
import app.vazovsky.coffe.domain.model.Product

interface CoffeRepository {

    suspend fun getMenu(id: String): List<Product>

    suspend fun getLocations(): List<Location>
}