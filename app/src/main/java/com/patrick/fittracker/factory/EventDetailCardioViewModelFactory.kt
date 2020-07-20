package com.patrick.fittracker.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.patrick.fittracker.calendar.eventcardio.EventDetailCardioViewModel
import com.patrick.fittracker.data.CardioRecord
import com.patrick.fittracker.data.source.FitTrackerRepository
import com.patrick.fittracker.linechart.cardiochart.CardioChartViewModel


//@Suppress("UNCHECKED_CAST")
//class EventDetailCardioViewModelFactory(
//    private val repository: FitTrackerRepository,
//    private val recordKey: CardioRecord
//) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//
//        if (modelClass.isAssignableFrom(EventDetailCardioViewModel::class.java)) {
//            return EventDetailCardioViewModel(
//                repository, recordKey
//            ) as T
//        }
//
//        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
//    }
//}