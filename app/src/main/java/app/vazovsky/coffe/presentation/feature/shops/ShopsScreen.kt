package app.vazovsky.coffe.presentation.feature.shops

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.vazovsky.coffe.R
import app.vazovsky.coffe.domain.model.CoffeeShop
import app.vazovsky.coffe.domain.model.Point
import app.vazovsky.coffe.presentation.ui.theme.Champagne
import app.vazovsky.coffe.presentation.ui.theme.CoyoteBrown
import app.vazovsky.coffe.presentation.ui.theme.PaleTaupe
import app.vazovsky.coffe.presentation.view.AppButton
import app.vazovsky.coffe.presentation.view.AppTopBar
import app.vazovsky.coffe.presentation.view.EmptyContent
import app.vazovsky.coffe.presentation.view.UnauthorizedDialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.util.concurrent.*
import timber.log.Timber
import kotlin.math.roundToInt

lateinit var locationProvider: FusedLocationProviderClient
lateinit var locationCallback: LocationCallback

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ShopsScreen(
    navigateToAuth: () -> Unit,
    onShopClick: (CoffeeShop) -> Unit,
    onMapClick: (List<CoffeeShop>) -> Unit,
) {
    val context = LocalContext.current
    val viewModel: ShopsViewModel = hiltViewModel()
    locationProvider = LocationServices.getFusedLocationProviderClient(context)

    val shops = viewModel.coffeeShops.observeAsState().value
    val error = viewModel.errorLiveData.observeAsState().value
    val currentLocation = viewModel.currentLocation.observeAsState().value
    val (showUnauthorizedDialog, setShowUnauthorizedDialog) = remember { mutableStateOf(false) }

    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )
    LaunchedEffect(locationPermissionsState) {
        if (!locationPermissionsState.allPermissionsGranted) {
            locationPermissionsState.launchMultiplePermissionRequest()
        }
    }

    LaunchedEffect(currentLocation) {
        Timber.d("LOCATION: $currentLocation")
    }

    DisposableEffect(locationProvider) {
        locationCallback = object : LocationCallback() {
            //1
            @SuppressLint("MissingPermission")
            override fun onLocationResult(result: LocationResult) {
                /**
                 * Option 1
                 * This option returns the locations computed, ordered from oldest to newest.
                 * */
                for (location in result.locations) {
                    // Update data class with location data
                    viewModel.currentLocation.value = Point(location.latitude, location.longitude)
                    Timber.d("LOCATION FOUNDED: ${location.latitude},${location.longitude}")
                }


                /**
                 * Option 2
                 * This option returns the most recent historical location currently available.
                 * Will return null if no historical location is available
                 * */
                locationProvider.lastLocation
                    .addOnSuccessListener { location ->
                        location?.let {
                            val lat = location.latitude
                            val long = location.longitude
                            // Update data class with location data
                            viewModel.currentLocation.value = Point(latitude = lat, longitude = long)
                        }
                    }
                    .addOnFailureListener {
                        Timber.e("${it.message}")
                    }

            }
        }

        if (!locationPermissionsState.allPermissionsGranted) {
            locationPermissionsState.launchMultiplePermissionRequest()
        } else {
            locationUpdate()
        }

        onDispose {
            stopLocationUpdate()
        }
    }

    SideEffect {
        if (shops == null && error == null) {
            viewModel.getNearestCoffeeShops {
                setShowUnauthorizedDialog(true)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.shops_topbar_title),
                onBackPressed = navigateToAuth,
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding(), start = 18.dp, end = 18.dp),
        ) {
            if (showUnauthorizedDialog) {
                UnauthorizedDialog {
                    setShowUnauthorizedDialog(false)
                    navigateToAuth()
                }
            }

            if (shops == null && error == null) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (shops.isNullOrEmpty()) {
                EmptyContent(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(R.string.shops_empty_content),
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 64.dp, top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    items(items = shops, key = { item -> item.id }) { coffeeShop ->
                        CoffeeShopCard(coffeeShop = coffeeShop, currentLocation = currentLocation) {
                            onShopClick(coffeeShop)
                        }
                    }
                }

                AppButton(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 20.dp),
                    text = stringResource(R.string.shops_maps),
                    onClick = { onMapClick(shops) },
                )
            }
        }
    }
}

@Composable
fun CoffeeShopCard(
    coffeeShop: CoffeeShop,
    currentLocation: Point?,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Champagne),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp, start = 10.dp, end = 10.dp),
            text = coffeeShop.name,
            color = CoyoteBrown,
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, start = 10.dp, end = 10.dp, bottom = 15.dp),
            text = if (currentLocation != null && coffeeShop.point != null) {
                val firstPoint = Location("StartLoc").apply {
                    latitude = currentLocation.latitude
                    longitude = currentLocation.longitude
                }
                val secondPoint = Location("EndLoc").apply {
                    latitude = coffeeShop.point.latitude
                    longitude = coffeeShop.point.longitude
                }

                val distanceInMeters = firstPoint.distanceTo(secondPoint)
                val isKilometres = distanceInMeters / 1000 >= 1

                buildString {
                    append(if (isKilometres) (distanceInMeters / 1000).roundToInt() else distanceInMeters)
                    append(" ")
                    append(stringResource(id = if (isKilometres) R.string.shops_distance_km else R.string.shops_distance_m))
                    append(" ")
                    append(stringResource(id = R.string.shops_distance_postfix))
                }
            } else {
                stringResource(R.string.shops_unknown_distance)
            },
            style = MaterialTheme.typography.bodySmall,
            color = PaleTaupe,
        )
    }
}

@SuppressLint("MissingPermission")
fun locationUpdate() {
    locationCallback.let {
        //An encapsulation of various parameters for requesting
        // location through FusedLocationProviderClient.
        val locationRequest: LocationRequest =
            LocationRequest.create().apply {
                interval = TimeUnit.SECONDS.toMillis(60)
                fastestInterval = TimeUnit.SECONDS.toMillis(30)
                maxWaitTime = TimeUnit.MINUTES.toMillis(2)
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
        //use FusedLocationProviderClient to request location update
        locationProvider.requestLocationUpdates(
            locationRequest,
            it,
            Looper.getMainLooper()
        )
    }
}

fun stopLocationUpdate() {
    try {
        //Removes all location updates for the given callback.
        val removeTask = locationProvider.removeLocationUpdates(locationCallback)
        removeTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Timber.d("Location Callback removed.")
            } else {
                Timber.d("Failed to remove Location Callback.")
            }
        }
    } catch (exception: SecurityException) {
        Timber.e("Failed to remove Location Callback.. $exception")
    }
}