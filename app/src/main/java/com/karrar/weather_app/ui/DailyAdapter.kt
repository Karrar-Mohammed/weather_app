package com.karrar.weather_app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.karrar.weather_app.R
import com.karrar.weather_app.data.domain.Daily
import com.karrar.weather_app.databinding.ItemDailyBinding
import com.karrar.weather_app.util.Constants
import com.karrar.weather_app.util.formatDate
import com.karrar.weather_app.util.loadWeatherIcon

class DailyAdapter(private val list: List<Daily>) :
    RecyclerView.Adapter<DailyAdapter.DailyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_daily, parent, false)
        return DailyViewHolder(view)
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        val currentItem = list[position]
        holder.binding.apply {
            textDayName.text = currentItem.dt?.formatDate(Constants.DateFormat.DAY).toString()
            textHumidityDaily.text = currentItem.humidity?.toInt().toString()
            textLowTemperature.text = textLowTemperature.context.getString(
                R.string.temperature,
                currentItem.temp?.min?.toInt().toString()
            )
            textHighTemperature.text = textHighTemperature.context.getString(
                R.string.temperature,
                currentItem.temp?.max?.toInt().toString()
            )
            imageDailyIcon.loadWeatherIcon(currentItem.weatherStatus?.get(0))
        }
    }

    override fun getItemCount() = list.size

    class DailyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemDailyBinding.bind(itemView)
    }
}
