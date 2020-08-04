package com.patrick.fittracker.record.classoption

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patrick.fittracker.data.Cardio
import com.patrick.fittracker.data.ClassOption
import com.patrick.fittracker.data.source.FitTrackerRepository


class ClassOptionRecordViewModel(private val repository: FitTrackerRepository, private val arguments: ClassOption) : ViewModel() {

    private val _classOption = MutableLiveData<ClassOption>().apply {
        value = arguments
    }

    val classOption: LiveData<ClassOption>
        get() = _classOption

    private val _navigateToRecord = MutableLiveData<String>()

    val navigateToRecord: LiveData<String>
        get() = _navigateToRecord

    fun navigateToRecord(arguments: String) {
        _navigateToRecord.value = arguments
    }




}
