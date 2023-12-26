package app.vazovsky.coffe.helper

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient

const val UPDATE_INTERVAL = 10000L
const val FASTEST_INTERVAL = 2000L

@SuppressLint("MissingPermission")
fun requestLocationUpdates(context: Context, onLocation: (Pair<Double, Double>) -> Unit) {
    val mLocationRequest = LocationRequest().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = UPDATE_INTERVAL
        fastestInterval = FASTEST_INTERVAL
    }
    val builder = LocationSettingsRequest.Builder()
    builder.addLocationRequest(mLocationRequest)
    val locationSettingsRequest = builder.build()


    val settingsClient = LocationServices.getSettingsClient(context)
    settingsClient.checkLocationSettings(locationSettingsRequest)
    getFusedLocationProviderClient(context).requestLocationUpdates(
        mLocationRequest,
        object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.apply {
                    onLocation(latitude to longitude)
                }
            }
        },
        Looper.getMainLooper()
    )
}

