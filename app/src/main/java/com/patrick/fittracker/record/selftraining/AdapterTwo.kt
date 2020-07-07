package com.patrick.fittracker.record.selftraining

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.patrick.fittracker.data.AddTrainingRecord
import com.patrick.fittracker.databinding.ItemRecordAddSetBinding
import com.patrick.fittracker.databinding.ItemRecordTwoBinding

class AdapterTwo():
    ListAdapter<AddTrainingRecord, AdapterTwo.RecordViewHolder>(
        DiffCallback
    ){

    class RecordViewHolder(private var binding: ItemRecordTwoBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(addTrainingRecord: AddTrainingRecord){
            binding.add = addTrainingRecord
            binding.executePendingBindings()

        }
    }


    companion object DiffCallback: DiffUtil.ItemCallback<AddTrainingRecord>(){
        override fun areContentsTheSame(oldItem: AddTrainingRecord, newItem: AddTrainingRecord): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: AddTrainingRecord, newItem: AddTrainingRecord): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder(
            ItemRecordTwoBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int){
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