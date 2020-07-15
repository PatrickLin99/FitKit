package com.patrick.fittracker.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.patrick.fittracker.data.CardioRecord
import com.patrick.fittracker.data.InsertRecord
import com.patrick.fittracker.data.source.FitTrackerRepository
import com.patrick.fittracker.linechart.WeightChartViewModel
import com.patrick.fittracker.linechart.cardiochart.CardioChartViewModel

@Suppress("UNCHECKED_CAST")
class CardioChartViewModelFactory(
    private val repository: FitTrackerRepository,
    private val recordKey: CardioRecord
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(CardioChartViewModel::class.java)) {
            return CardioChartViewModel(
                repository, recordKey
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}