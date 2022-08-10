package com.karrar.weather_app.data.network

import com.google.gson.Gson
import com.karrar.weather_app.data.domain.WeatherModel
import com.karrar.weather_app.util.Constants
import com.karrar.weather_app.util.State
import okhttp3.*

object Client {
    private val client = OkHttpClient()

   fun getWeatherData(): State<WeatherModel?> {

       val request = Request.Builder().url(getWeatherUrl()).build()
       val response =  client.newCall(request).execute()
       return if (response.isSuccessful){

           val result = Gson().fromJson( response.body?.string(), WeatherModel::class.java)
           State.Success(result)

       } else{
           State.Error(response.message)
       }


    }

    private fun getWeatherUrl(): HttpUrl {
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