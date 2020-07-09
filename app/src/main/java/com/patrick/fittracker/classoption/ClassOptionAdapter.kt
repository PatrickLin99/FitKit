package com.patrick.fittracker.classoption

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.patrick.fittracker.data.Cardio
import com.patrick.fittracker.data.ClassOption
import com.patrick.fittracker.databinding.ItemCardioSelectionBinding
import com.patrick.fittracker.databinding.ItemClassOptionBinding

//class ClassOptionAdapter {
//}
//class ClassOptionAdapter():
class ClassOptionAdapter(val onClickListener: OnClickListener):
    ListAdapter<ClassOption, ClassOptionAdapter.ClassOptionViewHolder>(
        DiffCallback
    ){

    class ClassOptionViewHolder(private var binding: ItemClassOptionBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(classoption : ClassOption){
            binding.classoption = classoption
            binding.executePendingBindings()

        }
    }


    companion object DiffCallback: DiffUtil.ItemCallback<ClassOption>(){
        override fun areContentsTheSame(oldItem: ClassOption, newItem: ClassOption): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: ClassOption, newItem: ClassOption): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassOptionViewHolder {
        return ClassOptionViewHolder(
            ItemClassOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ClassOptionViewHolder, position: Int){
        val classoption = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(classoption)
        }
        holder.bind(classoption)
    }

    class OnClickListener(val clickListener: (classoption : ClassOption) -> Unit) {
        fun onClick(classoption : ClassOption) = clickListener(classoption)
    }
}