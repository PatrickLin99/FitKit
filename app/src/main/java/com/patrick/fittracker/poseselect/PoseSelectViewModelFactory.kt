package com.patrick.fittracker.poseselect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.patrick.fittracker.data.SelectedMuscleGroup

class PoseSelectViewModelFactory(
    private val menu: SelectedMuscleGroup
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PoseSelectViewModel::class.java)) {
                return PoseSelectViewModel(menu) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")

        }
}