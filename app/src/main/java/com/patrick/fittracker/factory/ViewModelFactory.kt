package com.patrick.fittracker.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.patrick.fittracker.MainViewModel
import com.patrick.fittracker.cardio.selection.CardioSelectionViewModel
import com.patrick.fittracker.classoption.ClassOptionViewModel
import com.patrick.fittracker.data.source.FitTrackerRepository
import com.patrick.fittracker.finish.FinishRecordViewModel
import com.patrick.fittracker.finish.classoption.ClassOptionFinishViewModel
import com.patrick.fittracker.home.HomeViewModel
import com.patrick.fittracker.login.LoginViewModel
import com.patrick.fittracker.profile.ProfileViewModel
import com.patrick.fittracker.profile.edit.EditProfileViewModel
import com.patrick.fittracker.record.cardio.CardioRecordViewModel
import com.patrick.fittracker.record.classoption.inner.InnerRecordViewModel
import com.patrick.fittracker.record.selftraining.RecordViewModel

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

                isAssignableFrom(HomeViewModel::class.java) ->
                    HomeViewModel(repository)


                isAssignableFrom(CardioSelectionViewModel::class.java) ->
                    CardioSelectionViewModel(
                        repository
                    )

                isAssignableFrom(ClassOptionViewModel::class.java) ->
                    ClassOptionViewModel(
                        repository
                    )

                isAssignableFrom(InnerRecordViewModel::class.java) ->
                    InnerRecordViewModel(
                        repository
                    )

                isAssignableFrom(ProfileViewModel::class.java) ->
                ProfileViewModel(
                    repository
                )

                isAssignableFrom(EditProfileViewModel::class.java) ->
                    EditProfileViewModel(
                        repository
                    )

                isAssignableFrom(FinishRecordViewModel::class.java) ->
                    FinishRecordViewModel(
                        repository
                    )

                isAssignableFrom(ClassOptionFinishViewModel::class.java) ->
                    ClassOptionFinishViewModel(
                        repository
                    )

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}