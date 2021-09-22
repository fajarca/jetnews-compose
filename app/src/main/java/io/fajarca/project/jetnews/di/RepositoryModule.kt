package io.fajarca.project.jetnews.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.fajarca.project.jetnews.infrastructure.apiclient.ApiClient
import io.fajarca.project.jetnews.infrastructure.apiclient.ApiClientImpl
import io.fajarca.project.jetnews.domain.repository.NewsRepository
import io.fajarca.project.jetnews.data.repository.NewsRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindApiClient(impl: ApiClientImpl): ApiClient

    @Binds
    abstract fun bindRepository(impl: NewsRepositoryImpl): NewsRepository
}