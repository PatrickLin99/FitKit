package com.patrick.fittracker.record.cardio

import android.util.Log
import androidx.databinding.InverseMethod
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patrick.fittracker.FitTrackerApplication
import com.patrick.fittracker.R
import com.patrick.fittracker.data.AddTrainingRecord
import com.patrick.fittracker.data.Cardio
import com.patrick.fittracker.data.CardioRecord
import com.patrick.fittracker.data.Result
import com.patrick.fittracker.data.source.FitTrackerRepository
import com.patrick.fittracker.network.LoadApiStatus
import com.patrick.fittracker.profile.CardioSelectionOutlineProvider
import com.patrick.fittracker.profile.ProfileAvatarOutlineProvider
import com.patrick.fittracker.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CardioRecordViewModel(private val repository: FitTrackerRepository, private val arguments: Cardio) : ViewModel() {

    private val _cardioItem = MutableLiveData<Cardio>().apply {
        value = arguments
    }

    val cardioItem: LiveData<Cardio>
    get() = _cardioItem



    private val _addCardioRecordd = MutableLiveData<CardioRecord>().apply {
        value = CardioRecord()
    }

    val addCardioRecordd: LiveData<CardioRecord>
        get() = _addCardioRecordd

    val _photoUpload = MutableLiveData<Boolean>().apply { value = null }

    val photoUpload : LiveData<Boolean>
        get() = _photoUpload

    val outlineProvider = CardioSelectionOutlineProvider()

    //---------------------------------------------------------------------------------------------------
    private val _leave = MutableLiveData<Boolean>()

    val leave: LiveData<Boolean>
        get() = _leave

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

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {

        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")

    }

    fun uploadCardioRecordData(cardioRecord: CardioRecord) {

        Log.d("Patrick", "uploadRecordData, addTrainingRecord=$cardioRecord")
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.addCardioRecord(cardioRecord)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    leave(true)
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                else -> {
                    _error.value = FitTrackerApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }

    fun uploadCardioStatusRecord() {
                Log.d("Patrick", "_addTrainingRecordd.value=${_addCardioRecordd.value}")

        _addCardioRecordd.value?.let {
//            it.weight = it.weight.minus(5)
            it.burnFat = it.burnFat
            it.duration = it.duration
            _addCardioRecordd.value = _addCardioRecordd.value
        }
    }

    fun showLoadingStatus(){
        _status.value = LoadApiStatus.LOADING
    }



    fun leave(needRefresh: Boolean = false) {
        _leave.value = needRefresh
    }

    fun onLeft() {
        _leave.value = null
    }

    @InverseMethod("convertLongToString")
    fun convertStringToLong(value: String): Long {
        return try {
            value.toLong().let {
                when (it) {
                    0L -> 1
                    else -> it
                }
            }
        } catch (e: NumberFormatException) {
            1
        }
    }

    fun convertLongToString(value: Long): String {
        return value.toString()
    }


}
