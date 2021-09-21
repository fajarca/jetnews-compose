package io.fajarca.project.jetnews.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import io.fajarca.project.jetnews.CoroutineDispatcherProvider
import io.fajarca.project.jetnews.CoroutineDispatcherProviderImpl
import io.fajarca.project.jetnews.FakeNewsRepository
import io.fajarca.project.jetnews.NewsRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindNavigator(impl: FakeNewsRepository): NewsRepository
}