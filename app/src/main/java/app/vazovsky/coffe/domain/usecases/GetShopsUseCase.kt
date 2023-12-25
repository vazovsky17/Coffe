package app.vazovsky.coffe.domain.usecases

import app.vazovsky.coffe.data.remote.base.Failure
import app.vazovsky.coffe.data.remote.base.Either
import app.vazovsky.coffe.data.repository.CoffeRepository
import app.vazovsky.coffe.domain.base.UseCase
import app.vazovsky.coffe.domain.model.Location
import javax.inject.Inject

/** Получение списка кофеен */
class GetShopsUseCase @Inject constructor(
    private val coffeRepository: CoffeRepository,
) : UseCase<UseCase.None, List<Location>>() {

    override suspend fun run(params: None): Either<Failure, List<Location>> {
        return coffeRepository.getLocations()
    }
}