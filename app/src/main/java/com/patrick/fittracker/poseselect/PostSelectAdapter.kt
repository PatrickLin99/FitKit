package com.patrick.fittracker.poseselect

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.patrick.fittracker.databinding.ItemMusclePostureBinding

//class PostSelectAdapter {
//}

//class PostSelectAdapter(val onClickListener: OnClickListener):
//    ListAdapter<Product, PostSelectAdapter.PostSelectViewHolder>(DiffCallback){
//
//    class PostSelectViewHolder(private var binding: PostSelectAdapter):
//        RecyclerView.ViewHolder(binding.root){
//        fun bind(product: Product){
//            binding.viewModel = product
//            binding.executePendingBindings()
//
//        }
//    }
//
//
//    companion object DiffCallback: DiffUtil.ItemCallback<Product>(){
//        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
//            return oldItem == newItem
//        }
//
//        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
//            return oldItem.id == newItem.id
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostSelectViewHolder{
//        return PostSelectViewHolder(ItemMusclePostureBinding.inflate(LayoutInflater.from(parent.context)))
//    }
//
//    override fun onBindViewHolder(holder: PostSelectViewHolder, position: Int){
//        val product = getItem(position)
//        holder.itemView.setOnClickListener {
//            onClickListener.onClick(product)
//        }
//        holder.bind(product)
//    }
//
//    class OnClickListener(val clickListener: (product: Product) -> Unit) {
//        fun onClick(product: Product) = clickListener(product)
//    }
//}