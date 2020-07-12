package com.patrick.fittracker.record.classoption.inner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.patrick.fittracker.data.AddTrainingRecord
import com.patrick.fittracker.data.FitDetail
import com.patrick.fittracker.databinding.ItemClassoptionInnerRecordBinding
import com.patrick.fittracker.databinding.ItemRecordAddSetBinding

class InnerRecordAdapter():
    ListAdapter<FitDetail, InnerRecordAdapter.InnerRecordViewHolder>(DiffCallback){

    class InnerRecordViewHolder(private var binding: ItemClassoptionInnerRecordBinding):
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerRecordViewHolder {
        return InnerRecordViewHolder(
            ItemClassoptionInnerRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: InnerRecordViewHolder, position: Int){
        val menu = getItem(position)
//        holder.itemView.setOnClickListener {
//            onClickListener.onClick(menu)
//        }
        holder.bind(menu)
    }
//
//    class OnClickListener(val clickListener: (fitDetail: FitDetail) -> Unit) {
//        fun onClick(fitDetail: FitDetail) = clickListener(fitDetail)
//    }
}