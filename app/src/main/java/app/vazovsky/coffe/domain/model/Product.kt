package app.vazovsky.coffe.domain.model

data class Product(
    val id: Int,
    val name: String,
    val imageURL: String?,
    val price: Int,
)