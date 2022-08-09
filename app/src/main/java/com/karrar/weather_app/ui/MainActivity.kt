package com.karrar.weather_app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.karrar.weather_app.R
import com.karrar.weather_app.data.domain.WeatherModel
import com.karrar.weather_app.data.network.Client
import com.karrar.weather_app.databinding.ActivityMainBinding
import com.karrar.weather_app.util.formatDate
import com.karrar.weather_app.util.loadWeatherIcon

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }

    private fun setup() {

        getWeatherInfo()

    }

    private fun getWeatherInfo() {
        Client.fetchData{ result ->
            runOnUiThread {
                bindDataToUi(result)
            }
        }
    }


    private fun bindDataToUi(weather: WeatherModel?) {
        binding.apply {
            textTemperature.text =
                getString(R.string.temperature, weather?.current?.temp?.toInt().toString())
            textTimezone.text = getString(R.string.esenyurt)
            textTimezoneHeader.text = getString(R.string.esenyurt)
            textFeelsLike.text = getString(R.string.feels_like, weather?.current?.feelsLike?.toInt().toString())
            textSunriseTime.text = weather?.current?.sunrise?.formatDate("h:mm a")
            textSunsetTime.text = weather?.current?.sunset?.formatDate("h:mm a")
            imageIcon.loadWeatherIcon(weather?.current?.weatherStatus?.get(0))

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

}