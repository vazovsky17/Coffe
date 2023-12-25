package app.vazovsky.coffe.di

import android.content.Context
import app.vazovsky.coffe.data.remote.CoffeApiService
import app.vazovsky.coffe.data.remote.MockCoffeApiService
import app.vazovsky.coffe.data.remote.SemimockCoffeApiService
import app.vazovsky.coffe.data.remote.base.CallAdapterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://147.78.66.203:3210/ "

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Singleton
    @Provides
    fun provideClient(
        @ApplicationContext context: Context,
    ): OkHttpClient = OkHttpClient.Builder().build()


    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideCallAdapterFactory(): CallAdapterFactory = CallAdapterFactory()

    @Singleton
    @Provides
    fun provideRetrofit(
        gson: Gson,
        client: OkHttpClient,
        callAdapterFactory: CallAdapterFactory,
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(callAdapterFactory)
        .client(client)
        .build()

    @Singleton
    @Provides
    fun provideApiService(
        retrofit: Retrofit,
    ): CoffeApiService = SemimockCoffeApiService(
        apiService = retrofit.create(CoffeApiService::class.java),
        mockApiService = MockCoffeApiService(),
    )
}