package com.karrar.weather_app.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.karrar.weather_app.R
import com.karrar.weather_app.data.domain.Current
import com.karrar.weather_app.data.domain.Daily
import com.karrar.weather_app.data.domain.Hourly
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
        callbacks()
        getWeatherInfo()
    }

    private fun callbacks() {
        binding.textViewTryAgain.setOnClickListener {
            getWeatherInfo()
        }
    }

    private fun getWeatherInfo() {
        val observable = Observable.create { emitter ->
            emitter.onNext(State.Loading)
            emitter.onNext(Client.getWeatherData())
        }
        subscribeOnWeatherObservable(observable)

    }

    private fun subscribeOnWeatherObservable(observable: Observable<State<WeatherModel?>>) {
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe ({ response ->
                when (response) {
                    State.Loading -> {
                        onLoading()
                    }
                    is State.Success -> {
                        onSuccess(response.data)
                    }
                    is State.Error -> {
                        onError()
                    }
                }
            },{
            onError()
        })
    }

    private fun onSuccess(weather: WeatherModel?) {
        binding.apply {
            progressBar.visibility = View.GONE
            parentConstraint.visibility = View.VISIBLE
            errorAnimation.visibility = View.GONE
            textViewErrorTitle.visibility = View.GONE
            textViewTryAgain.visibility = View.GONE
        }

        setupCurrentDayWeather(weather?.current)
        setupDailyRecycler(weather?.daily)
        setupHourlyRecycler(weather?.hourly)
    }

    private fun onLoading() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            parentConstraint.visibility = View.GONE
            errorAnimation.visibility = View.GONE
            textViewErrorTitle.visibility = View.GONE
            textViewTryAgain.visibility = View.GONE
        }
    }

    private fun onError() {
        binding.apply {
            progressBar.visibility = View.GONE
            parentConstraint.visibility = View.GONE
            errorAnimation.visibility = View.VISIBLE
            textViewErrorTitle.visibility = View.VISIBLE
            textViewTryAgain.visibility = View.VISIBLE
        }
    }

    private fun setupCurrentDayWeather(currentWeather: Current?) {
        binding.apply {
            textTemperature.text =
                getString(R.string.temperature, currentWeather?.temp?.toInt().toString())

            textTimezone.text = getString(R.string.esenyurt)
            textTimezoneHeader.text = getString(R.string.esenyurt)
            textFeelsLike.text =
                getString(R.string.feels_like, currentWeather?.feelsLike?.toInt().toString())

            textSunriseTime.text = currentWeather?.sunrise?.formatDate(Constants.DateFormat.HOUR)
            textSunsetTime.text = currentWeather?.sunset?.formatDate(Constants.DateFormat.HOUR)
            imageIcon.loadWeatherIcon(currentWeather?.weatherStatus?.get(0))
        }
    }

    private fun setupDailyRecycler(dailyWeather: List<Daily>?) {
        binding.apply {
            dailyWeather?.let {
                val list = dailyWeather.take(7)
                val adapter = DailyAdapter(list)
                recyclerDaily.adapter = adapter
                recyclerDaily.suppressLayout(true)
            }
        }
    }

    private fun setupHourlyRecycler(hourlyWeather: List<Hourly>?) {
        binding.apply {
            hourlyWeather?.let {
                val list = hourlyWeather.take(24)
                val adapter = HourlyAdapter(list)
                recyclerHourly.adapter = adapter
            }
        }
    }
}