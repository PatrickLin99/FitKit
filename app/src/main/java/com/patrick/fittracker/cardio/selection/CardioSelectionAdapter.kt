package com.patrick.fittracker.cardio.selection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.patrick.fittracker.data.Cardio
import com.patrick.fittracker.databinding.ItemCardioSelectionBinding

//class CardioSelectionAdapter {
//}

class CardioSelectionAdapter(val onClickListener: OnClickListener):
//class CardioSelectionAdapter():
    ListAdapter<Cardio, CardioSelectionAdapter.CardioSelectionViewHolder>(
        DiffCallback
    ){

    class CardioSelectionViewHolder(private var binding: ItemCardioSelectionBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(cardio:Cardio){
            binding.cardio = cardio
            binding.executePendingBindings()

        }
    }


    companion object DiffCallback: DiffUtil.ItemCallback<Cardio>(){
        override fun areContentsTheSame(oldItem: Cardio, newItem: Cardio): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Cardio, newItem: Cardio): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardioSelectionViewHolder {
        return CardioSelectionViewHolder(
            ItemCardioSelectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CardioSelectionViewHolder, position: Int){
        val cardio = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(cardio)
        }
        holder.bind(cardio)
    }

    class OnClickListener(val clickListener: (cardio: Cardio) -> Unit) {
        fun onClick(cardio: Cardio) = clickListener(cardio)
    }
}