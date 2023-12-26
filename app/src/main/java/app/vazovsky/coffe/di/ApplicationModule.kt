package app.vazovsky.coffe.di

import android.app.Activity
import android.app.Application
import android.content.SharedPreferences
import app.vazovsky.coffe.data.preferences.PreferenceStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        app: Application
    ): SharedPreferences = app.getSharedPreferences(
        PreferenceStore.DEFAULT_PREFERENCE, Activity.MODE_PRIVATE
    )

    @Provides
    @Singleton
    fun providePreferenceStore(): PreferenceStore = PreferenceStore()
}