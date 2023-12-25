package app.vazovsky.coffe.di

import app.vazovsky.coffe.data.repository.AuthRepository
import app.vazovsky.coffe.data.repository.AuthRepositoryImpl
import app.vazovsky.coffe.data.repository.CoffeRepository
import app.vazovsky.coffe.data.repository.CoffeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(repository: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindCoffeRepository(repository: CoffeRepositoryImpl): CoffeRepository
}