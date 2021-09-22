package io.fajarca.project.jetnews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.fajarca.project.jetnews.data.db.AppDatabase
import io.fajarca.project.jetnews.data.db.TopHeadlineDao

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun provideMovieDao(database: AppDatabase): TopHeadlineDao {
        return database.topHeadlineDao()
    }

}