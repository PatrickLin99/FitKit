package com.patrick.fittracker.analysis

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.patrick.fittracker.data.FitDetail
import com.patrick.fittracker.data.InsertRecord
import com.patrick.fittracker.databinding.ItemWeightAnalysisBinding
import com.patrick.fittracker.databinding.ItemWeightDetailAnalysisBinding


class AnalysisDetailAdapter():
ListAdapter<FitDetail, AnalysisDetailAdapter.AnalysisDetailViewHolder>(DiffCallback){

    class AnalysisDetailViewHolder(private var binding: ItemWeightDetailAnalysisBinding):
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnalysisDetailViewHolder{
        return AnalysisDetailViewHolder(ItemWeightDetailAnalysisBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AnalysisDetailViewHolder, position: Int){
        val fitDetail = getItem(position)
//        holder.itemView.setOnClickListener {
//            onClickListener.onClick(insertRecord)
//        }
        holder.bind(fitDetail)
    }

    class OnClickListener(val clickListener: (insertRecord: InsertRecord) -> Unit) {
        fun onClick(insertRecord: InsertRecord) = clickListener(insertRecord)
    }
}