package io.fajarca.project.jetnews

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatcherProvider {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}
