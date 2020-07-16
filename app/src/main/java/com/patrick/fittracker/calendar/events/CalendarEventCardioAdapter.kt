package com.patrick.fittracker.calendar.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.patrick.fittracker.TimeUtil
import com.patrick.fittracker.data.CardioRecord
import com.patrick.fittracker.data.InsertRecord
import com.patrick.fittracker.databinding.ItemCalendarEvenCardioBinding
import com.patrick.fittracker.databinding.ItemCalendarEventBinding
import java.util.*

//class CalendarEventAdapter(val onClickListener: OnClickListener):
class CalendarEventCardioAdapter():
    ListAdapter<CardioRecord, CalendarEventCardioAdapter.CalendarEventCardioViewHolder>(DiffCallback){

    class CalendarEventCardioViewHolder(private var binding: ItemCalendarEvenCardioBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(cardioRecord: CardioRecord){
            binding.cardio = cardioRecord
            binding.executePendingBindings()

            val event_time = TimeUtil.CalendarStampToDate(cardioRecord.createdTime, Locale.TAIWAN)
            binding.calendarEventCardioTime.text = event_time

        }
    }


    companion object DiffCallback: DiffUtil.ItemCallback<CardioRecord>(){
        override fun areContentsTheSame(oldItem: CardioRecord, newItem: CardioRecord): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: CardioRecord, newItem: CardioRecord): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarEventCardioViewHolder{
        return CalendarEventCardioViewHolder(ItemCalendarEvenCardioBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CalendarEventCardioViewHolder, position: Int){
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