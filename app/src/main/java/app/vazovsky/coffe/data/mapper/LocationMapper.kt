package app.vazovsky.coffe.data.mapper

import app.vazovsky.coffe.data.remote.response.LocationResponse
import app.vazovsky.coffe.data.remote.response.PointResponse
import app.vazovsky.coffe.domain.model.Location
import app.vazovsky.coffe.domain.model.Point
import app.vazovsky.coffe.extensions.orDefault
import javax.inject.Inject

class LocationMapper @Inject constructor() {

    fun fromApiToModel(apiModel: LocationResponse?): Location {
        return Location(
            id = apiModel?.id.orDefault(),
            name = apiModel?.name.orDefault(),
            point = apiModel?.point?.let { point ->
                fromApiToModel(point)
            },
        )
    }

    private fun fromApiToModel(apiModel: PointResponse?): Point {
        return Point(
            latitude = apiModel?.latitude.orDefault(),
            longitude = apiModel?.longitude.orDefault(),
        )
    }
}