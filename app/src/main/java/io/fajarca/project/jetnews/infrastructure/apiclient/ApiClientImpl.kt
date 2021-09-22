package io.fajarca.project.jetnews.infrastructure.apiclient

import io.fajarca.project.jetnews.infrastructure.apiclient.exception.ClientErrorException
import io.fajarca.project.jetnews.infrastructure.apiclient.exception.NoInternetConnection
import io.fajarca.project.jetnews.infrastructure.apiclient.exception.ServerErrorException
import io.fajarca.project.jetnews.infrastructure.apiclient.exception.TimeoutException
import io.fajarca.project.jetnews.infrastructure.apiclient.exception.UnknownNetworkErrorException
import io.fajarca.project.jetnews.domain.Either
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.HttpException

@Singleton
class ApiClientImpl @Inject constructor() : ApiClient {

    override suspend fun <T> call(apiCall: suspend () -> T): Either<Exception, T> {
        return try {
            Either.Right(apiCall())
        } catch (exception: Exception) {
            handleError(exception)
        }
    }

    private fun handleError(exception: Exception): Either<Exception, Nothing> {
        val clientErrorResponseCode = 400..499
        val serverErrorResponsecode = 500..599
        return when (exception) {

            is HttpException -> {
                when (exception.code()) {
                    in clientErrorResponseCode -> Either.Left(ClientErrorException(exception.code()))
                    in serverErrorResponsecode -> Either.Left(ServerErrorException(exception.code()))
                    else -> Either.Left(
                        UnknownNetworkErrorException("Response code ${exception.code()} is invalid")
                    )
                }
            }
            is UnknownHostException -> Either.Left(NoInternetConnection())
            is SocketTimeoutException -> Either.Left(TimeoutException())
            else -> Either.Left(UnknownNetworkErrorException("Undefined error"))
        }
    }

}
