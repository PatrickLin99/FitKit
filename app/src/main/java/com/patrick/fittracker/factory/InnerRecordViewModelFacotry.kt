package com.patrick.fittracker.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.patrick.fittracker.data.source.FitTrackerRepository
import com.patrick.fittracker.record.classoption.inner.InnerRecordViewModel

//@Suppress("UNCHECKED_CAST")
//class InnerRecordViewModelFactory(
//    private val repository: FitTrackerRepository,
//    private val classKey: String
//) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//
//        if (modelClass.isAssignableFrom(InnerRecordViewModel::class.java)) {
//            return InnerRecordViewModel(
//                repository, classKey
//            ) as T
//        }
//
//        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
//    }
//}