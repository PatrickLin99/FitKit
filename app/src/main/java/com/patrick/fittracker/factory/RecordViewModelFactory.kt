package com.patrick.fittracker.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.patrick.fittracker.data.source.FitTrackerRepository
import com.patrick.fittracker.record.selftraining.RecordViewModel
import com.patrick.fittracker.record.SetOrderFilter

/**
 * Created by Wayne Chen on 2020-01-15.
 *
 * Factory for all ViewModels.
 */
@Suppress("UNCHECKED_CAST")
class RecordViewModelFactory(
    private val repository: FitTrackerRepository,
    private val groupp: SetOrderFilter
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(RecordViewModel::class.java)) {
            return RecordViewModel(
                repository,
                groupp
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}