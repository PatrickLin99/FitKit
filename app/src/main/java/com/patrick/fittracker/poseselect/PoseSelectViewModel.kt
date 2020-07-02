package com.patrick.fittracker.poseselect

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patrick.fittracker.data.SelectedMuscleGroup

class PoseSelectViewModel(selectedMuscleGroup: SelectedMuscleGroup) : ViewModel() {

    private val _movementList= MutableLiveData<List<String>>()

    val movementList: LiveData<List<String>>
        get() = _movementList

    init {
//        _movementList.value = SelectedMuscleGroup(listOf())
    }
}

