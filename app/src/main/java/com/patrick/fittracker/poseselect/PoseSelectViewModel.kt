package com.patrick.fittracker.poseselect

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patrick.fittracker.data.SelectedMuscleGroup

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

//        private val _movementList= MutableLiveData<List<SelectedMuscleGroup>>()
//
//    val movementList: LiveData<List<SelectedMuscleGroup>>
//        get() = _movementList
//
//    private val _navigateToRecord = MutableLiveData<SelectedMuscleGroup>()
//
//    val navigateToRecord: LiveData<SelectedMuscleGroup>
//        get() = _navigateToRecord
//    fun navigateToRecord(selectedMuscleGroup: SelectedMuscleGroup) {
//        _navigateToRecord.value = selectedMuscleGroup
//    }

}

