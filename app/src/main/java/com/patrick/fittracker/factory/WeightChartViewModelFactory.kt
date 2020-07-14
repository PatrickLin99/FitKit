package com.patrick.fittracker.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.patrick.fittracker.data.InsertRecord
import com.patrick.fittracker.data.source.FitTrackerRepository
import com.patrick.fittracker.linechart.WeightChartViewModel
import com.patrick.fittracker.record.selftraining.RecordViewModel



@Suppress("UNCHECKED_CAST")
class WeightChartViewModelFactory(
    private val repository: FitTrackerRepository,
    private val recordKey: InsertRecord
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(WeightChartViewModel::class.java)) {
            return WeightChartViewModel(
                repository, recordKey
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}