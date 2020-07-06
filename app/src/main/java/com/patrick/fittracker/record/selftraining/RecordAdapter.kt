package com.patrick.fittracker.record.selftraining

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.patrick.fittracker.databinding.ItemRecordAddSetBinding


//class RecordAdapter(val onClickListener: OnClickListener):
class RecordAdapter():
    ListAdapter<String, RecordAdapter.RecordViewHolder>(
        DiffCallback
    ){

    class RecordViewHolder(private var binding: ItemRecordAddSetBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(menu: String){
            binding.orderNum = menu
            binding.executePendingBindings()

        }
    }


    companion object DiffCallback: DiffUtil.ItemCallback<String>(){
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder(
            ItemRecordAddSetBinding.inflate(LayoutInflater.from(parent.context))
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