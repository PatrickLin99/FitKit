package com.patrick.fittracker.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.patrick.fittracker.data.Cardio
import com.patrick.fittracker.data.ClassOption
import com.patrick.fittracker.data.source.FitTrackerRepository
import com.patrick.fittracker.record.cardio.CardioRecordViewModel
import com.patrick.fittracker.record.classoption.ClassOptionRecordViewModel


@Suppress("UNCHECKED_CAST")
class ClassOptionRecordViewModelFactory(
    private val repository: FitTrackerRepository,
    private val classOption: ClassOption
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ClassOptionRecordViewModel::class.java)) {
            return ClassOptionRecordViewModel(repository, classOption) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}