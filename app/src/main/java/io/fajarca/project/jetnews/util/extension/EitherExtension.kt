package io.fajarca.project.jetnews.util.extension

import io.fajarca.project.jetnews.domain.Either

fun <L,R> Either<L, R>.getOrNull() : R? {
    return when(this) {
        is Either.Left -> null
        is Either.Right -> data
    }
}