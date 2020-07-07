package com.patrick.fittracker.ext

import androidx.fragment.app.Fragment
import com.patrick.fittracker.FitTrackerApplication
import com.patrick.fittracker.data.AddTrainingRecord
import com.patrick.fittracker.data.Cardio
import com.patrick.fittracker.data.ClassOption
import com.patrick.fittracker.factory.*
import com.patrick.fittracker.group.MuscleGroupTypeFilter
import com.patrick.fittracker.record.selftraining.SetOrderFilter

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

//fun Fragment.getVmFactory(addTrainingRecord: AddTrainingRecord): RecordViewModelFactory {
//    val repository = (requireContext().applicationContext as FitTrackerApplication).repository
//    return RecordViewModelFactory(repository, addTrainingRecord)
//}

fun Fragment.getVmFactory(cardio: Cardio): CardioSelectionRecordViewModelFactory {
    val repository = (requireContext().applicationContext as FitTrackerApplication).repository
    return CardioSelectionRecordViewModelFactory(repository, cardio)
}

fun Fragment.getVmFactory(classOption: ClassOption): ClassOptionRecordViewModelFactory {
    val repository = (requireContext().applicationContext as FitTrackerApplication).repository
    return ClassOptionRecordViewModelFactory(repository, classOption)
}

fun Fragment.getVmFactory(muscleKey: String): RecordViewModelFactory {
    val repository = (requireContext().applicationContext as FitTrackerApplication).repository
    return RecordViewModelFactory(repository, muscleKey)
}


