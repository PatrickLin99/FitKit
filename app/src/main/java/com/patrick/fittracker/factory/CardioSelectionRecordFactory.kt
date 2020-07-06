package com.patrick.fittracker.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.patrick.fittracker.data.Cardio
import com.patrick.fittracker.data.source.FitTrackerRepository
import com.patrick.fittracker.record.cardio.CardioRecordViewModel


@Suppress("UNCHECKED_CAST")
class CardioSelectionRecordViewModelFactory(
    private val repository: FitTrackerRepository,
    private val cardio: Cardio
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(CardioRecordViewModel::class.java)) {
            return CardioRecordViewModel(repository, cardio) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}