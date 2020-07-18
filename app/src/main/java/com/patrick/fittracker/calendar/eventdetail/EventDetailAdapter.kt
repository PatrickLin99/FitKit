package com.patrick.fittracker.calendar.eventdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.patrick.fittracker.TimeUtil
import com.patrick.fittracker.data.FitDetail
import com.patrick.fittracker.data.InsertRecord
import com.patrick.fittracker.databinding.ItemCalendarEventBinding
import com.patrick.fittracker.databinding.ItemCalendarEventDetailBinding
import java.util.*

//class EventDetailAdapter(val onClickListener: OnClickListener):
class EventDetailAdapter():
    ListAdapter<FitDetail, EventDetailAdapter.EventDetailViewHolder>(DiffCallback){

    class EventDetailViewHolder(private var binding: ItemCalendarEventDetailBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(fitDetail: FitDetail){
            binding.fitdetail = fitDetail
            binding.executePendingBindings()



        }
    }


    companion object DiffCallback: DiffUtil.ItemCallback<FitDetail>(){
        override fun areContentsTheSame(oldItem: FitDetail, newItem: FitDetail): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: FitDetail, newItem: FitDetail): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventDetailViewHolder{
        return EventDetailViewHolder(ItemCalendarEventDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: EventDetailViewHolder, position: Int){
        val menu = getItem(position)
//        holder.itemView.setOnClickListener {
//            onClickListener.onClick(menu)
//        }
        holder.bind(menu)
    }

    class OnClickListener(val clickListener: (menu: FitDetail) -> Unit) {
        fun onClick(menu: FitDetail) = clickListener(menu)
    }
}