package com.karrar.weather_app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.karrar.weather_app.R
import com.karrar.weather_app.data.domain.Hourly
import com.karrar.weather_app.databinding.ItemHourlyBinding
import com.karrar.weather_app.util.formatDate

class HourlyAdapter(private val list: List<Hourly>): RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hourly, parent, false)
        return HourlyViewHolder(view)
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        val currentItem = list[position]
        holder.binding.apply {
            textHourlyTemp.text = currentItem.temp?.toInt().toString() + "°"
            textHourlyHumidity.text = currentItem.humidity.toString()
            imageHourlyIcon.setImageResource(R.drawable.sun)
            textTimeHour.text = currentItem.dt?.formatDate("h a").toString()
        }
    }

    override fun getItemCount() = list.size



    class HourlyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding = ItemHourlyBinding.bind(itemView)
    }
}