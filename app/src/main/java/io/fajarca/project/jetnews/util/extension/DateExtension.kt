package io.fajarca.project.jetnews.util.extension

import java.util.*
import java.util.concurrent.TimeUnit

fun Date.getDifferenceInHours(): Long {
    val differenceInMillis =  Date().time - this.time
    return TimeUnit.MILLISECONDS.toHours(differenceInMillis)
}

