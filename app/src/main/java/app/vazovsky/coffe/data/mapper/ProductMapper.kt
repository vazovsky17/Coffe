package app.vazovsky.coffe.data.mapper

import app.vazovsky.coffe.data.remote.response.ProductResponse
import app.vazovsky.coffe.domain.model.Product
import app.vazovsky.coffe.extensions.orDefault
import javax.inject.Inject

class ProductMapper @Inject constructor() {

    fun fromApiToModel(apiModel: ProductResponse?): Product {
        return Product(
            id = apiModel?.id.orDefault(),
            name = apiModel?.name.orDefault(),
            imageURL = apiModel?.imageURL,
            price = apiModel?.price.orDefault(),
        )
    }
}