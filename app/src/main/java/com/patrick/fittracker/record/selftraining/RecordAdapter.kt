package com.patrick.fittracker.record.selftraining

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.patrick.fittracker.data.FitDetail
import com.patrick.fittracker.databinding.ItemRecordAddSetBinding


class RecordAdapter():
    ListAdapter<FitDetail, RecordAdapter.RecordViewHolder>(DiffCallback){

    class RecordViewHolder(private var binding: ItemRecordAddSetBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(fitDetail: FitDetail){
            binding.fitDetail = fitDetail
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder(
            ItemRecordAddSetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int){
        val menu = getItem(position)
        holder.bind(menu)
    }
}