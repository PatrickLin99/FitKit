package com.patrick.fittracker.record.selftraining

import android.util.Log
import androidx.databinding.InverseMethod
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import com.google.common.io.Files.map
import com.patrick.fittracker.FitTrackerApplication
import com.patrick.fittracker.R
import com.patrick.fittracker.data.*
import com.patrick.fittracker.data.source.FitTrackerRepository
import com.patrick.fittracker.network.LoadApiStatus
import com.patrick.fittracker.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RecordViewModel(private val repository: FitTrackerRepository,
                      private val muscleKey: String
) : ViewModel() {

    private val _addItem = MutableLiveData<FitDetail>().apply {
        value = FitDetail()
    }

    val addItem: LiveData<FitDetail>
        get() = _addItem

    private val _addInsert = MutableLiveData<MutableList<FitDetail>>().apply {
        value = mutableListOf()
    }

    val addInsert: LiveData<MutableList<FitDetail>>
        get() = _addInsert


    val photoUpload = MutableLiveData<Boolean>().apply { value = null }

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

    var orderNum: Long = 0
    fun showRecordList(){
        orderNum += 1
        addItem.value?.let {
         val recordList = FitDetail(it.orderSet?:0, it.weight?:0, orderNum)
         showRecyclerView(recordList)
        }
    }

    private fun showRecyclerView(fitDetail: FitDetail){
        _addInsert.value?.add(0, fitDetail)
        _addInsert.value = _addInsert.value
    }

    fun valueInsert(imageUri: String){
        addInsert.value?.let { fitDetailList ->
            uploadRecord(insertRecord = InsertRecord(muscleKey, fitDetailList, 0, imageUri))
        }
    }


    private fun uploadRecord(insertRecord: InsertRecord) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.addSelfRecord(insertRecord)) {
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

    fun showLoadingStatus(){
        _status.value = LoadApiStatus.LOADING
        }

    fun leave(needRefresh: Boolean = false) {
            _leave.value = needRefresh
        }

    fun onLeft() {
            _leave.value = null
        }

    fun plusWeight() {
        _addItem.value?.let {
            it.weight = it.weight.plus(5)
            _addItem.value = _addItem.value
        }
    }

    fun minusWeight() {
        _addItem.value?.let {
            if (it.weight >= 5) {
                it.weight = it.weight.minus(5)
                _addItem.value = _addItem.value
            } else {
                it.weight = 5
                _addItem.value = _addItem.value
            }
        }
    }

    fun plusOrderSet() {
        _addItem.value?.let {
            it.orderSet = it.orderSet.plus(1)
            _addItem.value = _addItem.value
        }
    }

    fun minusOrderSet() {
        _addItem.value?.let {
            if (it.orderSet > 0) {
                it.orderSet = it.orderSet.minus(1)
                _addItem.value = _addItem.value
            } else {
                it.orderSet = 1
                _addItem.value = _addItem.value
            }
        }
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
