package com.karrar.weather_app.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.karrar.weather_app.R
import com.karrar.weather_app.data.domain.WeatherModel
import com.karrar.weather_app.databinding.ActivityMainBinding
import com.karrar.weather_app.util.formatDate
import com.karrar.weather_app.util.loadWeatherIcon
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }

    private fun setup() {
        getWeatherInfo()

    }


    private fun getWeatherInfo() {
        val request = Request.Builder().url(getWeatherUrl()).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("TAG", "onFailure: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string().let { jsonString ->
                    val result = Gson().fromJson(jsonString, WeatherModel::class.java)
                    runOnUiThread {
                        bindDataToUi(result)
                    }
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun bindDataToUi(weather: WeatherModel?) {
        binding.apply {
            textTemperature.text = weather?.current?.temp?.toInt().toString() + "°"
            textTimezone.text = resources.getString(R.string.esenyurt)
            textFeelsLike.text = "Feels Like ${weather?.current?.feelsLike?.toInt().toString() + "°"}"
            textSunriseTime.text = weather?.current?.sunrise?.formatDate("h:mm a")
            textSunsetTime.text = weather?.current?.sunset?.formatDate("h:mm a")
            imageIcon.loadWeatherIcon(weather?.current?.weather?.get(0))

            weather?.hourly?.let {
                val list = it.take(24)
                val adapter = HourlyAdapter(list)
                recyclerHourly.adapter = adapter
            }

            weather?.daily?.let {
                val list = it.take(7)
                val adapter = DailyAdapter(list)
                recyclerDaily.adapter = adapter
                recyclerDaily.suppressLayout(true)
            }

        }
    }


    private fun getWeatherUrl(): HttpUrl {
        return HttpUrl.Builder()
            .scheme("http")
            .host("api.openweathermap.org")
            .addPathSegment("data")
            .addPathSegment("2.5")
            .addPathSegment("onecall")
            .addQueryParameter("lat","41.034283")
            .addQueryParameter("lon","28.680119")
            .addQueryParameter("appid","25acea668518011d09cbb69aad983022")
            .addQueryParameter("units", "metric")
            .build()
    }
}