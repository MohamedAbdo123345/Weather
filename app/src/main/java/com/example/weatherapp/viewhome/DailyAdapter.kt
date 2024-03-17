package com.example.weatherapp.viewhome

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.dto.DailyItem
import com.example.weatherapp.dto.HourlyItem
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale
import java.util.TimeZone

class DailyAdapter(private val context: Context,var timeZone: TimeZone):ListAdapter<DailyItem,DailyAdapter.DailyViewHolder>(DailyDiffUtil())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyAdapter.DailyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell, parent, false)
        return DailyViewHolder(view)
    }

    override fun onBindViewHolder(holder: DailyAdapter.DailyViewHolder, position: Int) {
        val currentItem = getItem(position)
        val  dtString:Int=currentItem.dt!!.toInt()

        Log.i("TAG", "onBindViewHolder542: $dtString")

        currentItem.dt.let {
            holder.dataa.setDay(dtString,timeZone)
            if(position==0){
                holder.dataa.text="Today"
            }
            else if(position==1){
                holder.dataa.text="Tomorrow"
            }

        }

       // holder.imagee.setImageBitmap(currentItem.)

    }
    class DailyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dataa: TextView = itemView.findViewById(R.id.textViewTime)
        val imagee: ImageView = itemView.findViewById(R.id.imageViewWeatherIcon)
        val temperaturee: TextView = itemView.findViewById(R.id.textViewTempDegree)
    }
    class DailyDiffUtil : DiffUtil.ItemCallback<DailyItem>() {
        override fun areItemsTheSame(oldItem: DailyItem, newItem: DailyItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DailyItem, newItem: DailyItem): Boolean {
            return oldItem == newItem
        }
    }


}

fun TextView.setDay(timeInSecond: Int, timeZone: TimeZone) {
    setText(getDayFormat(timeInSecond * 1000L,timeZone))
}
fun getDayFormat(timeInMilliSecond: Long,timeZone : TimeZone): Int{
    timeZone.id

    val calendar = GregorianCalendar(timeZone)
    calendar.timeInMillis = timeInMilliSecond
    return when(calendar.get(Calendar.DAY_OF_WEEK)){
        1-> R.string.sunday
        2-> R.string.monday
        3-> R.string.tuesday
        4-> R.string.wednesday
        5-> R.string.thursday
        6-> R.string.friday
        else -> R.string.saturday
            }
}