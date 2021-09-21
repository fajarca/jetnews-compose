package io.fajarca.project.jetnews

import javax.inject.Inject
import kotlinx.coroutines.delay

class FakeNewsRepository @Inject constructor() : NewsRepository {

    override suspend fun getNews(): List<News> {
        delay(3000)
        return listOf(News(1, "John Wick 3"), News(2, "Hometown"))
    }

}