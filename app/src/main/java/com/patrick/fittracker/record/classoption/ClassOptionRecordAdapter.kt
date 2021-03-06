package com.patrick.fittracker.record.classoption

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.patrick.fittracker.data.Cardio
import com.patrick.fittracker.data.ClassOption
import com.patrick.fittracker.databinding.ItemCardioSelectionBinding
import com.patrick.fittracker.databinding.ItemClassRecordMenuBinding


class ClassOptionRecordAdapter(val onClickListener: OnClickListener):
    ListAdapter<String, ClassOptionRecordAdapter.ClassOptionRecordViewHolder>(DiffCallback){

    class ClassOptionRecordViewHolder(private var binding: ItemClassRecordMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(classOption: String) {
            binding.classoption = classOption
            binding.executePendingBindings()

        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassOptionRecordViewHolder {
        return ClassOptionRecordViewHolder(
            ItemClassRecordMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ClassOptionRecordViewHolder, position: Int) {
        val classOption = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(classOption)
        }
        holder.bind(classOption)
    }

    class OnClickListener(val clickListener: (classOption: String) -> Unit) {
        fun onClick(classOption: String) = clickListener(classOption)
    }
}