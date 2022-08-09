package com.karrar.weather_app.util


import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ViewTarget
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
    val default = "http://openweathermap.org/img/w/${weatherStatus?.icon}.png"
    weatherIcon.getOrDefault(weatherStatus?.id,default)
    weatherIcon.get(weatherStatus?.id)?.let { this.setImageResource(it) }
}