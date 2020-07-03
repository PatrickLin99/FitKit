package com.patrick.fittracker.ext

import androidx.fragment.app.Fragment
import com.patrick.fittracker.FitTrackerApplication
import com.patrick.fittracker.data.SelectedMuscleGroup
import com.patrick.fittracker.factory.SelectedMuscleViewModelFactory
import com.patrick.fittracker.factory.ViewModelFactory
import com.patrick.fittracker.group.MuscleGroupTypeFilter

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Extension functions for Fragment.
 */
fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as FitTrackerApplication).repository
    return ViewModelFactory(repository)
}

fun Fragment.getVmFactory(group: MuscleGroupTypeFilter): SelectedMuscleViewModelFactory {
    val repository = (requireContext().applicationContext as FitTrackerApplication).repository
    return SelectedMuscleViewModelFactory(repository, group)
}