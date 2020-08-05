package com.patrick.fittracker.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.patrick.fittracker.MainViewModel
import com.patrick.fittracker.analysis.AnalysisViewModel
import com.patrick.fittracker.analysis.cardioanalysis.AnalysisCardioViewModel
import com.patrick.fittracker.analysis.test.AnalysisTestViewModel
import com.patrick.fittracker.analysis.weight.AnalysisWeightViewModel
import com.patrick.fittracker.calendar.CalendarViewModel
import com.patrick.fittracker.calendar.eventcardio.EventDetailCardioViewModel
import com.patrick.fittracker.calendar.eventdetail.EventDetailViewModel
import com.patrick.fittracker.cardio.selection.CardioSelectionViewModel
import com.patrick.fittracker.classoption.ClassOptionViewModel
import com.patrick.fittracker.data.source.FitTrackerRepository
import com.patrick.fittracker.finish.FinishRecordViewModel
import com.patrick.fittracker.finish.cardiofinish.CardioFinishViewModel
import com.patrick.fittracker.finish.classoption.ClassOptionFinishViewModel
import com.patrick.fittracker.home.HomeViewModel
import com.patrick.fittracker.linechart.WeightChartViewModel
import com.patrick.fittracker.linechart.cardiochart.CardioChartViewModel
import com.patrick.fittracker.location.LocationViewModel
import com.patrick.fittracker.login.LoginViewModel
import com.patrick.fittracker.profile.ProfileViewModel
import com.patrick.fittracker.profile.edit.EditProfileViewModel
import com.patrick.fittracker.record.cardio.CardioRecordViewModel
import com.patrick.fittracker.record.classoption.inner.InnerRecordViewModel
import com.patrick.fittracker.record.selftraining.RecordViewModel
import com.patrick.fittracker.timer.CountDownTimerViewModel

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

                isAssignableFrom(AnalysisViewModel::class.java) ->
                    AnalysisViewModel(
                        repository
                    )

                isAssignableFrom(CardioFinishViewModel::class.java) ->
                    CardioFinishViewModel(
                        repository
                    )

                isAssignableFrom(AnalysisTestViewModel::class.java) ->
                    AnalysisTestViewModel(
                        repository
                    )

                isAssignableFrom(AnalysisWeightViewModel::class.java) ->
                    AnalysisWeightViewModel(
                        repository
                    )

                isAssignableFrom(AnalysisCardioViewModel::class.java) ->
                    AnalysisCardioViewModel(
                        repository
                    )

                isAssignableFrom(CalendarViewModel::class.java) ->
                    CalendarViewModel(
                        repository
                    )

                isAssignableFrom(EventDetailViewModel ::class.java) ->
                    EventDetailViewModel(
                        repository
                    )

                isAssignableFrom(EventDetailCardioViewModel ::class.java) ->
                    EventDetailCardioViewModel(
                        repository
                    )

                isAssignableFrom(CountDownTimerViewModel ::class.java) ->
                    CountDownTimerViewModel(
                        repository
                    )

                isAssignableFrom(LocationViewModel ::class.java) ->
                    LocationViewModel(
                        repository
                    )

                        else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}