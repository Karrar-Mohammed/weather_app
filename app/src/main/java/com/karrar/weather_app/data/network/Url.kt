package com.karrar.weather_app.data.network

import com.karrar.weather_app.util.Constants
import okhttp3.HttpUrl

object Url {
     fun getWeatherUrl(): HttpUrl {
        return HttpUrl.Builder()
            .scheme(Constants.Api.PROTOCOL)
            .host(Constants.Api.HOST)
            .addPathSegments("data/2.5/onecall")
            .addQueryParameter(Constants.Api.LAT, "41.034283")
            .addQueryParameter(Constants.Api.LON, "28.680119")
            .addQueryParameter(Constants.Api.APPID, Constants.Api.KEY)
            .addQueryParameter(Constants.Api.UNITS, "metric")
            .build()
    }
}