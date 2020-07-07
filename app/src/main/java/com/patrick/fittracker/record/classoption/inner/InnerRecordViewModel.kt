package com.patrick.fittracker.record.classoption.inner

import android.util.Log
import androidx.databinding.InverseMethod
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patrick.fittracker.FitTrackerApplication
import com.patrick.fittracker.R
import com.patrick.fittracker.data.AddTrainingRecord
import com.patrick.fittracker.data.RecordSetOrder
import com.patrick.fittracker.data.Result
import com.patrick.fittracker.data.source.FitTrackerRepository
import com.patrick.fittracker.network.LoadApiStatus
import com.patrick.fittracker.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class InnerRecordViewModel(private val repository: FitTrackerRepository) : ViewModel() {

    private val _addTrainingRecordd = MutableLiveData<AddTrainingRecord>().apply {
        value = AddTrainingRecord()
    }

    val addTrainingRecordd: LiveData<AddTrainingRecord>
        get() = _addTrainingRecordd

    private val _add = MutableLiveData<List<AddTrainingRecord>>()

    val add: LiveData<List<AddTrainingRecord>>
        get() = _add


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

//        if (FitTrackerApplication.instance.isLiveDataDesign()) {
//            getLiveRecordResult(muscleKey)
//        } else {
//            getArticlesResult()
//        }
    }

    fun uploadRecordData(addTrainingRecord: AddTrainingRecord) {

        Log.d("Patrick", "uploadRecordData, addTrainingRecord=$addTrainingRecordd")
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.addClassRecord(addTrainingRecord)) {
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

    fun getClassInnerRecordResult(classKey: String) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getClassRecord(classKey)

            _add.value = when (result) {
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


    fun leave(needRefresh: Boolean = false) {
        _leave.value = needRefresh
    }

    fun onLeft() {
        _leave.value = null
    }

    fun plusWeight() {
//        Log.d("Patrick", "plusWeight")
//        Log.d("Patrick", "_addTrainingRecordd.value=${_addTrainingRecordd.value}")
        _addTrainingRecordd.value?.let {

//            Log.d("Patrick", "it.weight=${it.weight}")
            it.weight = it.weight.plus(5)
            _addTrainingRecordd.value = _addTrainingRecordd.value
        }
    }
    fun minusWeight() {
        _addTrainingRecordd.value?.let {
            it.weight = it.weight.minus(5)
            _addTrainingRecordd.value = _addTrainingRecordd.value
        }
    }

    fun plusOrderSet() {
        _addTrainingRecordd.value?.let {
            it.orderSet = it.orderSet.plus(1)
            _addTrainingRecordd.value = _addTrainingRecordd.value
        }
    }
    fun minusOrderSet() {
        _addTrainingRecordd.value?.let {
            it.orderSet = it.orderSet.minus(1)
            _addTrainingRecordd.value = _addTrainingRecordd.value
        }
    }

}
