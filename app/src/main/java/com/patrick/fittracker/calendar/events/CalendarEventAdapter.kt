package com.patrick.fittracker.calendar.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.patrick.fittracker.TimeUtil
import com.patrick.fittracker.data.InsertRecord
import com.patrick.fittracker.databinding.ItemCalendarEventBinding
import com.patrick.fittracker.databinding.ItemMusclePostureBinding
import java.util.*


//class CalendarEventAdapter(val onClickListener: OnClickListener):
class CalendarEventAdapter():
    ListAdapter<InsertRecord, CalendarEventAdapter.CalendarEventViewHolder>(DiffCallback){

    class CalendarEventViewHolder(private var binding: ItemCalendarEventBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(insertrecord: InsertRecord){
            binding.insertrecord = insertrecord
            binding.executePendingBindings()

            val event_time = TimeUtil.CalendarStampToDate(insertrecord.createdTime, Locale.TAIWAN)
            binding.calendarEventTime.text = event_time

        }
    }


    companion object DiffCallback: DiffUtil.ItemCallback<InsertRecord>(){
        override fun areContentsTheSame(oldItem: InsertRecord, newItem: InsertRecord): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: InsertRecord, newItem: InsertRecord): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarEventViewHolder{
        return CalendarEventViewHolder(ItemCalendarEventBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CalendarEventViewHolder, position: Int){
        val menu = getItem(position)
//        holder.itemView.setOnClickListener {
//            onClickListener.onClick(menu)
//        }
        holder.bind(menu)
    }

    class OnClickListener(val clickListener: (menu: String) -> Unit) {
        fun onClick(menu: String) = clickListener(menu)
    }
}