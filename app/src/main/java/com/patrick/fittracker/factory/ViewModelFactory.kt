package com.patrick.fittracker.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.patrick.fittracker.MainViewModel
import com.patrick.fittracker.cardio.CardioSelectionViewModel
import com.patrick.fittracker.data.source.FitTrackerRepository
import com.patrick.fittracker.group.GroupViewModel
import com.patrick.fittracker.home.HomeViewModel
import com.patrick.fittracker.record.RecordViewModel

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
                    CardioSelectionViewModel(repository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}