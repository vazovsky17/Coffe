package app.vazovsky.coffe

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class CoffeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
        Hawk.init(this).build()
    }
}