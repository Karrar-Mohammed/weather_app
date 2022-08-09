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
    when (weatherStatus?.id) {
        800 -> Glide.with(this)
            .load(R.drawable.clear_sky)
            .into(this)
        801, 804 -> Glide.with(this)
            .load(R.drawable.clouds)
            .into(this)
        500 -> Glide.with(this)
            .load(R.drawable.rain)
            .into(this)
        else -> Glide.with(this)
            .load(Constants.Api.URL_IMAGE + weatherStatus?.icon + Constants.Api.IMAGE_EXTENSION)
            .into(this)
    }
}