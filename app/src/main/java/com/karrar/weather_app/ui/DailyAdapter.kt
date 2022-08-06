package com.karrar.weather_app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.karrar.weather_app.R
import com.karrar.weather_app.data.domain.Daily
import com.karrar.weather_app.databinding.ItemDailyBinding
import com.karrar.weather_app.util.formatDate
import com.karrar.weather_app.util.loadWeatherIcon

class DailyAdapter(val list: List<Daily>): RecyclerView.Adapter<DailyAdapter.DailyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_daily, parent, false)
        return DailyViewHolder(view)
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        val currentItem = list[position]
        holder.binding.apply {
            textDayName.text = currentItem.dt?.formatDate("EEEE").toString()
            textHumidityDaily.text = currentItem.humidity.toString()
            textLowTemperature.text = currentItem.temp?.min?.toInt().toString()+ "°"
            textHighTemperature.text = currentItem.temp?.max?.toInt().toString()+ "°"
            imageDailyIcon.loadWeatherIcon(currentItem.weather?.get(0))
        }
    }

    override fun getItemCount() = list.size

    class DailyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding = ItemDailyBinding.bind(itemView)
    }
}
