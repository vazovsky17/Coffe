package app.vazovsky.coffe.domain.usecases

import app.vazovsky.coffe.data.remote.exception.Failure
import app.vazovsky.coffe.data.remote.exception.Either
import app.vazovsky.coffe.data.repository.CoffeRepository
import app.vazovsky.coffe.domain.base.UseCase
import app.vazovsky.coffe.domain.model.CoffeeShop
import javax.inject.Inject

/** Получение списка кофеен */
class GetShopsUseCase @Inject constructor(
    private val coffeRepository: CoffeRepository,
) : UseCase<UseCase.None, List<CoffeeShop>>() {

    override suspend fun run(params: None): Either<Failure, List<CoffeeShop>> {
        return coffeRepository.getLocations()
    }
}