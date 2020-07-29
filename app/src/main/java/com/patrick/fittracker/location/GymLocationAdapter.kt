package com.patrick.fittracker.location

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.patrick.fittracker.classoption.ClassOptionAdapter
import com.patrick.fittracker.data.ClassOption
import com.patrick.fittracker.data.DetailResults
import com.patrick.fittracker.data.GymLocationListResult
import com.patrick.fittracker.databinding.ItemClassOptionBinding
import com.patrick.fittracker.databinding.ItemGymLocatiomBinding

class GymLocationAdapter():
//class GymLocationAdapter(val onClickListener: OnClickListener):
    ListAdapter<DetailResults, GymLocationAdapter.GymLocationViewHolder>(
        DiffCallback
    ){

    class GymLocationViewHolder(private var binding: ItemGymLocatiomBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(gymList : DetailResults){
            binding.gymlist = gymList
            binding.executePendingBindings()
//            val size = binding.viewModel?.detailResult?.value?.size?.minus(1)
//            for (i in 0..size!!){
//                binding.textView11.text = binding.viewModel?.detailResult?.value?.get(i)?.name
//                Log.d("aaaa0000000000000","${binding.viewModel?.detailResult?.value?.get(i)?.name}")
//            }

        }
    }


    companion object DiffCallback: DiffUtil.ItemCallback<DetailResults>(){
        override fun areContentsTheSame(oldItem: DetailResults, newItem: DetailResults): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: DetailResults, newItem: DetailResults): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GymLocationViewHolder {
        return GymLocationViewHolder(
            ItemGymLocatiomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: GymLocationViewHolder, position: Int){
        val gymList = getItem(position)
//        holder.itemView.setOnClickListener {
//            onClickListener.onClick(classoption)
//        }
        holder.bind(gymList)
    }

    class OnClickListener(val clickListener: (gymList: DetailResults) -> Unit) {
        fun onClick(gymList: DetailResults) = clickListener(gymList)
    }
}