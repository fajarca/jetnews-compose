package io.fajarca.project.jetnews.util.date

sealed class TimeDifference {
    data class Minute(val minutes: Int) : TimeDifference()
    data class Hours(val hours: Int) : TimeDifference()
    data class Day(val days: Int) : TimeDifference()
    object Unknown : TimeDifference()
}