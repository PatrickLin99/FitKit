package com.patrick.fittracker.analysis

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.patrick.fittracker.TimeUtil
import com.patrick.fittracker.data.InsertRecord
import com.patrick.fittracker.databinding.ItemMusclePostureBinding
import com.patrick.fittracker.databinding.ItemWeightAnalysisBinding
import com.patrick.fittracker.databinding.TestLayoutBinding
import com.patrick.fittracker.poseselect.PostSelectAdapter
import java.sql.Time
import java.util.*


class AnalysisAdapter(val onClickListener: OnClickListener):
//class AnalysisAdapter():
    ListAdapter<InsertRecord, AnalysisAdapter.AnalysisViewHolder>(DiffCallback){

    class AnalysisViewHolder(private var binding: ItemWeightAnalysisBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(insertRecord: InsertRecord){
            binding.insertrecord = insertRecord
            binding.executePendingBindings()
            binding.analysisWorkoutTime.text = TimeUtil.CalendarStampToDate(insertRecord.createdTime, Locale.TAIWAN)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnalysisViewHolder{
        return AnalysisViewHolder(ItemWeightAnalysisBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AnalysisViewHolder, position: Int){
        val insertRecord = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(insertRecord)
        }
        holder.bind(insertRecord)
    }

    class OnClickListener(val clickListener: (insertRecord: InsertRecord) -> Unit) {
        fun onClick(insertRecord: InsertRecord) = clickListener(insertRecord)
    }
}