package com.karrar.weather_app.util


import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


fun Int.getHourFromTimeStamp(): String {
    val localTime = Instant.ofEpochSecond(this.toLong())
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
    return localTime.format(DateTimeFormatter.ofPattern("h a"))

}