package io.fajarca.project.jetnews.infrastructure.apiclient.exception

data class UnknownNetworkErrorException(val errorMessage : String) : Exception()