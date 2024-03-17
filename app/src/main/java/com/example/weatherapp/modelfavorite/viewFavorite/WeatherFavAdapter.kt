package com.example.weatherapp.modelfavorite.viewFavorite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.dto.DailyItem
import com.example.weatherapp.dto.LocationDTO
import com.example.weatherapp.viewhome.DailyAdapter

class WeatherFavAdapter(private val context: Context,private val onClickListener: OnClickListtenerr):
    ListAdapter<LocationDTO, WeatherFavAdapter.WeatherFAvViewHolder>(LocationDiffUtil())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherFavAdapter.WeatherFAvViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favitem, parent, false)
        return WeatherFAvViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherFavAdapter.WeatherFAvViewHolder, position: Int) {
        val currentItem = getItem(position)
        val  dtString=currentItem.cityName
        val latitiude=currentItem.latitude
        val longtudue=currentItem.longitude
        holder.city.text=dtString
        holder.deletIcon.setOnClickListener{
onClickListener.OnClick(currentItem)
        }
        holder.locationData.setOnClickListener{
            onClickListener.onLocationDataClick(latitiude, longtudue)
        }

    }
    class WeatherFAvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val city: TextView = itemView.findViewById(R.id.textViewCity)
        val deletIcon:ImageView=itemView.findViewById(R.id.deleticon)
        val locationData:ImageView=itemView.findViewById(R.id.locationdata)

    }
    class LocationDiffUtil : DiffUtil.ItemCallback<LocationDTO>() {
        override fun areItemsTheSame(oldItem: LocationDTO, newItem: LocationDTO): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: LocationDTO, newItem: LocationDTO): Boolean {
            return oldItem == newItem
        }
    }


}