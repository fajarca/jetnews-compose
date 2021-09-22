package io.fajarca.project.jetnews.infrastructure.apiclient.exception

data class ClientErrorException(val code : Int) : Exception()