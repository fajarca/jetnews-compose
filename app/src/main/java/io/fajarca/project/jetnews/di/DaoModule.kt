package io.fajarca.project.jetnews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.fajarca.project.jetnews.data.db.AppDatabase
import io.fajarca.project.jetnews.data.db.ArticleDao
import io.fajarca.project.jetnews.data.db.SearchHistoryDao

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun provideMovieDao(database: AppDatabase): ArticleDao {
        return database.articleDao()
    }

    @Provides
    fun provideSearchHistoryDao(database: AppDatabase): SearchHistoryDao {
        return database.searchHistoryDao()
    }
}