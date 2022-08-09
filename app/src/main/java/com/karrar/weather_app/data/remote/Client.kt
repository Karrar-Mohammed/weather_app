package com.karrar.weather_app.data.remote

import android.util.Log
import com.google.gson.Gson
import com.karrar.weather_app.data.domain.WeatherModel
import okhttp3.*
import java.io.IOException

object Client {
    private val client = OkHttpClient()

   fun fetchData(myCallback: (result: WeatherModel?) -> Unit){

        val request = Request.Builder().url(getWeatherUrl()).build()

        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                response.body?.string().let { jsonString ->
                    val result = Gson().fromJson(jsonString, WeatherModel::class.java)
                    myCallback.invoke(result)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.d("TAG", "onFailure: ${e.message}")
            }
        })
    }

    private fun getWeatherUrl(): HttpUrl {
        return HttpUrl.Builder()
            .scheme("http")
            .host("api.openweathermap.org")
            .addPathSegment("data")
            .addPathSegment("2.5")
            .addPathSegment("onecall")
            .addQueryParameter("lat", "41.034283")
            .addQueryParameter("lon", "28.680119")
            .addQueryParameter("appid", "25acea668518011d09cbb69aad983022")
            .addQueryParameter("units", "metric")
            .build()
    }
}