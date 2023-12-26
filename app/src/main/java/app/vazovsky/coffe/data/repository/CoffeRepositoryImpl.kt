package app.vazovsky.coffe.data.repository

import app.vazovsky.coffe.data.remote.exception.Failure
import app.vazovsky.coffe.data.mapper.LocationMapper
import app.vazovsky.coffe.data.mapper.ProductMapper
import app.vazovsky.coffe.data.remote.exception.Either
import app.vazovsky.coffe.data.remote.exception.map
import app.vazovsky.coffe.data.remote.CoffeApiService
import app.vazovsky.coffe.domain.model.Location
import app.vazovsky.coffe.domain.model.Product
import javax.inject.Inject

class CoffeRepositoryImpl @Inject constructor(
    private val apiService: CoffeApiService,
    private val locationMapper: LocationMapper,
    private val productMapper: ProductMapper,
) : CoffeRepository {

    override suspend fun getMenu(id: String): Either<Failure, List<Product>> {
        return apiService.getMenu(id).map { products ->
            products.map { product ->
                productMapper.fromApiToModel(product)
            }
        }
    }

    override suspend fun getLocations(): Either<Failure, List<Location>> {
        return apiService.getLocations().map { locations ->
            locations.map { location ->
                locationMapper.fromApiToModel(location)
            }
        }
    }
}