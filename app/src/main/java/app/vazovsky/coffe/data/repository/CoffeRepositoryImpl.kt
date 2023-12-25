package app.vazovsky.coffe.data.repository

import app.vazovsky.coffe.data.mapper.LocationMapper
import app.vazovsky.coffe.data.mapper.ProductMapper
import app.vazovsky.coffe.data.remote.CoffeApiService
import app.vazovsky.coffe.domain.model.Location
import app.vazovsky.coffe.domain.model.Product
import javax.inject.Inject

class CoffeRepositoryImpl @Inject constructor(
    private val apiService: CoffeApiService,
    private val locationMapper: LocationMapper,
    private val productMapper: ProductMapper,
) : CoffeRepository {

    override suspend fun getMenu(id: String): List<Product> {
        return apiService.getMenu(id).map { product ->
            productMapper.fromApiToModel(product)
        }
    }

    override suspend fun getLocations(): List<Location> {
        return apiService.getLocations().map { location ->
            locationMapper.fromApiToModel(location)
        }
    }
}