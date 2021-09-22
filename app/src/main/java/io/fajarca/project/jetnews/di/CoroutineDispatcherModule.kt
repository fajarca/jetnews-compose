package io.fajarca.project.jetnews.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.fajarca.project.jetnews.infrastructure.coroutine.CoroutineDispatcherProvider
import io.fajarca.project.jetnews.infrastructure.coroutine.CoroutineDispatcherProviderImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class CoroutineDispatcherModule {

    @Binds
    abstract fun bindNavigator(impl: CoroutineDispatcherProviderImpl): CoroutineDispatcherProvider
}