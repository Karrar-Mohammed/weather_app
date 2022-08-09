package com.karrar.weather_app.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.karrar.weather_app.R
import com.karrar.weather_app.data.domain.WeatherModel
import com.karrar.weather_app.data.network.Client
import com.karrar.weather_app.databinding.ActivityMainBinding
import com.karrar.weather_app.util.Constants
import com.karrar.weather_app.util.State
import com.karrar.weather_app.util.formatDate
import com.karrar.weather_app.util.loadWeatherIcon
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

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
        val observable = Observable.create { emitter ->
            emitter.onNext(State.Loading)
            emitter.onNext(Client.getWeatherData())
        }
    }

    private fun bindDataToUi(weather: WeatherModel?) {
        binding.apply {
            textTemperature.text =
                getString(R.string.temperature, weather?.current?.temp?.toInt().toString())
            textTimezone.text = getString(R.string.esenyurt)
            textTimezoneHeader.text = getString(R.string.esenyurt)
            textFeelsLike.text =
                getString(R.string.feels_like, weather?.current?.feelsLike?.toInt().toString())
            textSunriseTime.text = weather?.current?.sunrise?.formatDate(Constants.DateFormat.HOUR)
            textSunsetTime.text = weather?.current?.sunset?.formatDate(Constants.DateFormat.HOUR)
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