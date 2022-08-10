package com.karrar.weather_app.util


import android.widget.ImageView
import com.bumptech.glide.Glide
import com.karrar.weather_app.R
import com.karrar.weather_app.data.domain.WeatherStatus
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


fun Int.formatDate(pattern: String): String {
    val localTime = Instant.ofEpochSecond(this.toLong())
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
    return localTime.format(DateTimeFormatter.ofPattern(pattern))

}

fun ImageView.loadWeatherIcon(weatherStatus: WeatherStatus?) {
    val weatherIcon: Map<Int,Int> = mapOf(
        800 to R.drawable.clear_sky,
        801 to R.drawable.clouds,
        804 to R.drawable.clouds,
        500 to R.drawable.rain,
    )
    val defaultIcon = Constants.Api.URL_IMAGE + weatherStatus?.icon + Constants.Api.IMAGE_EXTENSION
    Glide.with(this.context).load(weatherIcon.getOrDefault(weatherStatus?.id, defaultIcon)).into(this)
}