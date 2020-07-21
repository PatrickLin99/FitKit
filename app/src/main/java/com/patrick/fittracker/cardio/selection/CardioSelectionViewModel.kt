package com.patrick.fittracker.cardio.selection

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patrick.fittracker.FitTrackerApplication
import com.patrick.fittracker.R
import com.patrick.fittracker.data.Cardio
import com.patrick.fittracker.data.source.FitTrackerRepository
import com.patrick.fittracker.data.Result
import com.patrick.fittracker.network.LoadApiStatus
import com.patrick.fittracker.profile.CardioSelectionOutlineProvider
import com.patrick.fittracker.profile.ProfileAvatarOutlineProvider
import com.patrick.fittracker.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CardioSelectionViewModel(private val repository: FitTrackerRepository) : ViewModel() {

    private val _cardio = MutableLiveData<List<Cardio>>()

    val cardio: LiveData<List<Cardio>>
        get() = _cardio

    var liveArticles = MutableLiveData<List<Cardio>>()

    private val _navigateToCardioRecord = MutableLiveData<Cardio>()

    val navigationToCardioRecord: LiveData<Cardio>
        get() = _navigateToCardioRecord

    val outlineProvider = ProfileAvatarOutlineProvider()

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    fun navigateToCardioRecord(cardio: Cardio) {
        _navigateToCardioRecord.value = cardio
    }

    fun navigateToCardioRecordDone () {
        _navigateToCardioRecord.value = null
    }



    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Call getArticlesResult() on init so we can display status immediately.
     */
    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")

//        if (FitTrackerApplication.instance.isLiveDataDesign()) {
            getCardioSelectionResult()
//        } else {
//            getCardioSelectionResult()
//        }
    }

    fun getCardioSelectionResult() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getCardioSelection()

            _cardio.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    result.data
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    _error.value = FitTrackerApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            _refreshStatus.value = false
        }
    }

    fun refresh() {

        if (FitTrackerApplication.instance.isLiveDataDesign()) {
            _status.value = LoadApiStatus.DONE
            _refreshStatus.value = false

        } else {
            if (status.value != LoadApiStatus.LOADING) {
                getCardioSelectionResult()
            }
        }
    }
}
