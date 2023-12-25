package app.vazovsky.coffe.domain.usecases

import app.vazovsky.coffe.data.repository.CoffeRepository
import app.vazovsky.coffe.domain.base.UseCaseUnary
import app.vazovsky.coffe.domain.model.Product
import javax.inject.Inject

/** Получение меню кофейни */
class GetMenuUseCase @Inject constructor(
    private val coffeRepository: CoffeRepository,
) : UseCaseUnary<GetMenuUseCase.Params, List<Product>>() {

    override suspend fun execute(params: Params): List<Product> {
        return coffeRepository.getMenu(id = params.id)
    }

    data class Params(
        val id: String,
    )
}