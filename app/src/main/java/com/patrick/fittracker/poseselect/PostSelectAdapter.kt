package com.patrick.fittracker.poseselect

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.patrick.fittracker.R
import com.patrick.fittracker.data.SelectedMuscleGroup
import com.patrick.fittracker.databinding.ItemMusclePostureBinding

//class PostSelectAdapter {
//}
//class PostSelectAdapter(val onClickListener: OnClickListener):
class PostSelectAdapter(val onClickListener: OnClickListener):
    ListAdapter<String, PostSelectAdapter.PostSelectViewHolder>(DiffCallback){

    class PostSelectViewHolder(private var binding: ItemMusclePostureBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(menu: String){
            binding.menu = menu
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostSelectViewHolder{
        return PostSelectViewHolder(ItemMusclePostureBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PostSelectViewHolder, position: Int){
        val menu = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(menu)
        }
        holder.bind(menu)
    }

    class OnClickListener(val clickListener: (menu: String) -> Unit) {
        fun onClick(menu: String) = clickListener(menu)
    }
}
