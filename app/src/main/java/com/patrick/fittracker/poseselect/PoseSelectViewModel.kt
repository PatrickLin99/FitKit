package com.patrick.fittracker.poseselect

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patrick.fittracker.data.SelectedMuscleGroup
import com.patrick.fittracker.network.LoadApiStatus

class PoseSelectViewModel(selectedMuscleGroup: SelectedMuscleGroup) : ViewModel() {

    private val _movementList= MutableLiveData<List<String>>()

    val movementList: LiveData<List<String>>
        get() = _movementList

    private val _navigateToRecord = MutableLiveData<String>()

    val navigateToRecord: LiveData<String>

    get() = _navigateToRecord
    fun navigateToRecord(selectedMuscleGroup: String) {
        _navigateToRecord.value = selectedMuscleGroup
    }

    private val _navigateToRecordTwo = MutableLiveData<SelectedMuscleGroup>()

    val navigateToRecordTwo: LiveData<SelectedMuscleGroup>
        get() = _navigateToRecordTwo
    fun navigateToRecordTwo(selectedMuscleGroup: SelectedMuscleGroup) {
        _navigateToRecordTwo.value = selectedMuscleGroup
    }


    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status



}

