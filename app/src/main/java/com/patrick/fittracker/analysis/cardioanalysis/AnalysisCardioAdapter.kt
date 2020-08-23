package com.patrick.fittracker.analysis.cardioanalysis

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.patrick.fittracker.R
import com.patrick.fittracker.data.CardioRecord
import com.patrick.fittracker.databinding.ItemCardioAnalysisBinding

class AnalysisCardioAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<CardioRecord, AnalysisCardioAdapter.AnalysisCardioViewHolder>(DiffCallback) {

    class AnalysisCardioViewHolder(private var binding: ItemCardioAnalysisBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cardioRecord: CardioRecord) {
            binding.cardiorecord = cardioRecord
            binding.executePendingBindings()

        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CardioRecord>() {
        override fun areContentsTheSame(oldItem: CardioRecord, newItem: CardioRecord): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: CardioRecord, newItem: CardioRecord): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnalysisCardioViewHolder {
        return AnalysisCardioViewHolder(
            ItemCardioAnalysisBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AnalysisCardioViewHolder, position: Int) {
        val cardioRecord = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(cardioRecord)
        }
        holder.bind(cardioRecord)
    }

    class OnClickListener(val clickListener: (cardioRecord: CardioRecord) -> Unit) {
        fun onClick(cardioRecord: CardioRecord) = clickListener(cardioRecord)
    }
}