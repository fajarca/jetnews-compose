package io.fajarca.project.jetnews.domain.repository

import io.fajarca.project.jetnews.domain.Either
import io.fajarca.project.jetnews.domain.entity.TopHeadline

interface NewsRepository {
    suspend fun getTopHeadlines() : Either<Exception, List<TopHeadline>>
}