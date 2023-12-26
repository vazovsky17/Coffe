package app.vazovsky.coffe.presentation.feature.map

import android.graphics.PointF
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import app.vazovsky.coffe.R
import app.vazovsky.coffe.domain.model.Location
import app.vazovsky.coffe.presentation.view.AppTopBar
import app.vazovsky.coffe.presentation.view.rememberMapViewWithLifecycle
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.TextStyle
import com.yandex.runtime.ui_view.ViewProvider

@Composable
fun MapScreen(
    shops: List<Location>,
    onBackPressed: () -> Unit,
) {
    val context = LocalContext.current

    val mapView = rememberMapViewWithLifecycle()

    fun moveCamera(point: Point, zoom: Float = 17f) {
        mapView.map.move(
            CameraPosition(point, zoom, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 2F),
            null
        )
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.map_topbar_title),
                onBackPressed = onBackPressed,
            )
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            AndroidView(
                factory = { mapView },
                modifier = Modifier.fillMaxSize()
            ) {
                shops.forEach { shop ->
                    shop.point?.let { shopPoint ->
                        val point = Point(shopPoint.latitude, shopPoint.longitude)

                        mapView.map.mapObjects.addPlacemark(
                            point,
                            ViewProvider(
                                View(context).apply {
                                    background = context.getDrawable(R.drawable.ic_coffee_shop)
                                }
                            ),
                            IconStyle().setAnchor(
                                PointF(0.5f, 1f)
                            ),
                        ).apply {
                            setText(
                                shop.name,
                                TextStyle().apply {
                                    size = 14F
                                    placement = TextStyle.Placement.BOTTOM
                                    offset = 5F
                                    color = context.getColor(R.color.coyote_brown)
                                }
                            )
                            zIndex = 100f
                            moveCamera(point)
                        }
                    }
                }
            }
        }
    }
}