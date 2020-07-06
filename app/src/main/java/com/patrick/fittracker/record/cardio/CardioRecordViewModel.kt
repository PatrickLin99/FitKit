package com.patrick.fittracker.record.cardio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patrick.fittracker.data.Cardio
import com.patrick.fittracker.data.source.FitTrackerRepository

class CardioRecordViewModel(private val repository: FitTrackerRepository, private val arguments: Cardio) : ViewModel() {
    private val _cardioItem = MutableLiveData<Cardio>().apply {
        value = arguments
    }

    val cardioItem: LiveData<Cardio>
    get() = _cardioItem
}
