package app.vazovsky.coffe.domain.usecases

import app.vazovsky.coffe.data.repository.CoffeRepository
import app.vazovsky.coffe.domain.base.UseCase
import app.vazovsky.coffe.domain.base.UseCaseUnary
import app.vazovsky.coffe.domain.model.Location
import javax.inject.Inject

/** Получение списка кофеен */
class GetShopsUseCase @Inject constructor(
    private val coffeRepository: CoffeRepository,
) : UseCaseUnary<UseCase.None, List<Location>>() {

    override suspend fun execute(params: UseCase.None): List<Location> {
        return coffeRepository.getLocations()
    }
}