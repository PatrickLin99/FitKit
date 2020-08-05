package com.patrick.fittracker.poseselect

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patrick.fittracker.data.SelectedMuscleGroup
import com.patrick.fittracker.network.LoadApiStatus

class PoseSelectViewModel(selectedMuscleGroup: SelectedMuscleGroup) : ViewModel() {

    private val _navigateToRecord = MutableLiveData<String>()

    val navigateToRecord: LiveData<String>
    get() = _navigateToRecord

    fun navigateToRecord(selectedMuscleGroup: String) {
        _navigateToRecord.value = selectedMuscleGroup
    }

}

