package com.example.weatherapp.viewhome

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.Convertr
import com.example.weatherapp.R
import com.example.weatherapp.dto.HourlyItem


class HourlyAdapter(private val context: Context) :
    ListAdapter<HourlyItem, HourlyAdapter.HourlyViewHolder>(HourlyDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell, parent, false)
        return HourlyViewHolder(view)
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        val currentItem = getItem(position)
        val curr=getItem(0)
        val dtString = Convertr.convertUnixTimeToHour(currentItem.dt.toString().toLong())
        Log.i("TAG", "onBindViewHolderdt: ${curr.dt.toString()}")
        Log.i("TAG", "onBindViewHoldertemp: ${curr.temp.toString()}")
        Log.i("TAG", "onBindViewHolderuvi: ${curr.uvi.toString()}")
        Log.i("TAG", "onBindViewHolderdewPoint: ${curr.dewPoint.toString()}")
        Log.i("TAG", "onBindViewHolderfeelsLike: ${curr.feelsLike.toString()}")
        Log.i("TAG", "onBindViewHolderfeelsLike: ${curr.pop.toString()}")


        holder.data.text = dtString
       
//        Glide.with(context)
//            .load(currentItem.thumbnail)
//            .centerCrop()
//            .placeholder(R.drawable.ic_launcher_foreground)
//            .into(holder.image)
        holder.temperature.text=currentItem.temp.toString()
   }

    class HourlyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val data: TextView = itemView.findViewById(R.id.textViewTime)
        val image: ImageView = itemView.findViewById(R.id.imageViewWeatherIcon)
        val temperature: TextView = itemView.findViewById(R.id.textViewTempDegree)
    }

    class HourlyDiffUtil : DiffUtil.ItemCallback<HourlyItem>() {
        override fun areItemsTheSame(oldItem: HourlyItem, newItem: HourlyItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: HourlyItem, newItem: HourlyItem): Boolean {
            return oldItem == newItem
        }
    }
}

