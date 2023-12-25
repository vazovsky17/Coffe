package app.vazovsky.coffe.domain.usecases

import app.vazovsky.coffe.data.remote.base.Failure
import app.vazovsky.coffe.data.remote.base.Either
import app.vazovsky.coffe.data.repository.CoffeRepository
import app.vazovsky.coffe.domain.base.UseCase
import app.vazovsky.coffe.domain.model.Product
import javax.inject.Inject

/** Получение меню кофейни */
class GetMenuUseCase @Inject constructor(
    private val coffeRepository: CoffeRepository,
) : UseCase<GetMenuUseCase.Params, List<Product>>() {

    override suspend fun run(params: Params): Either<Failure, List<Product>> {
        return coffeRepository.getMenu(id = params.id)
    }

    data class Params(
        val id: String,
    )
}