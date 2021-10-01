package io.fajarca.project.jetnews.util.date

import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DateManager @Inject constructor() {

    companion object {
        private const val ONE_HOUR = 1
        private const val ONE_DAY = 24
    }

    fun getTimeDifference(startDate: Date, endDate: Date): TimeDifference {
        val differenceInMillis = startDate.time - endDate.time
        val timeDifferenceInHours = TimeUnit.MILLISECONDS.toHours(differenceInMillis)

        return when {
            timeDifferenceInHours < ONE_HOUR -> {
                val timeDifferenceInMinutes = TimeUnit.MILLISECONDS.toMinutes(differenceInMillis)
                TimeDifference.Minute(timeDifferenceInMinutes.toInt())
            }
            timeDifferenceInHours <= ONE_DAY -> TimeDifference.Hours(timeDifferenceInHours.toInt())
            timeDifferenceInHours > ONE_DAY -> {
                val timeDifferenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMillis)
                TimeDifference.Day(timeDifferenceInDays.toInt())
            }
            else -> TimeDifference.Unknown
        }
    }

}