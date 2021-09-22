package io.fajarca.project.jetnews.infrastructure.apiclient.exception

data class ServerErrorException(val code : Int) : Exception()