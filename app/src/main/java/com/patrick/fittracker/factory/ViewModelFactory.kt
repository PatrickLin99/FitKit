package com.patrick.fittracker.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.patrick.fittracker.MainViewModel
import com.patrick.fittracker.cardio.selection.CardioSelectionViewModel
import com.patrick.fittracker.classoption.ClassOptionViewModel
import com.patrick.fittracker.data.source.FitTrackerRepository
import com.patrick.fittracker.record.cardio.CardioRecordViewModel

/**
 * Created by Wayne Chen on 2020-01-15.
 *
 * Factory for all ViewModels.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val repository: FitTrackerRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(repository)

                isAssignableFrom(CardioSelectionViewModel::class.java) ->
                    CardioSelectionViewModel(
                        repository
                    )

                isAssignableFrom(ClassOptionViewModel::class.java) ->
                    ClassOptionViewModel(
                        repository
                    )

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}