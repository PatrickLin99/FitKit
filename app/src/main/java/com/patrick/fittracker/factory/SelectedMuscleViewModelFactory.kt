package com.patrick.fittracker.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.patrick.fittracker.data.source.FitTrackerRepository
import com.patrick.fittracker.group.GroupViewModel
import com.patrick.fittracker.group.MuscleGroupTypeFilter


/**
 *
 * Factory for all ViewModels.
 */
@Suppress("UNCHECKED_CAST")
class SelectedMuscleViewModelFactory(
    private val repository: FitTrackerRepository,
    private val group: MuscleGroupTypeFilter
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(GroupViewModel::class.java)) {
            return GroupViewModel(repository, group) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}