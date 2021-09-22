package io.fajarca.project.jetnews.infrastructure.apiclient

import io.fajarca.project.jetnews.domain.Either

interface ApiClient {
    suspend fun <T> call(apiCall: suspend () -> T): Either<Exception, T>
}