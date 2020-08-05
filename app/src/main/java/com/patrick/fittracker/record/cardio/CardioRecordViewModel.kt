package com.patrick.fittracker.record.cardio

import android.util.Log
import androidx.databinding.InverseMethod
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patrick.fittracker.FitTrackerApplication
import com.patrick.fittracker.R
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

//--------------------------------------------------------------------------------------------------

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

    private fun uploadCardioRecordData(cardioRecord: CardioRecord) {

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

    fun calculateCal(type: String){
        addCardioRecordd.value?.let {
            when (cardioItem.value?.cardio_title) {
                "羽毛球" -> it.burnFat = it.duration.times(153).div(30)
                "棒球" -> it.burnFat = it.duration.times(141).div(30)
                "籃球" -> it.burnFat = it.duration.times(189).div(30)
                "保齡球" -> it.burnFat = it.duration.times(108).div(30)
                "攀岩" -> it.burnFat = it.duration.times(210).div(30)
                "自行車" -> it.burnFat = it.duration.times(252).div(30)
                "慢跑" -> it.burnFat = it.duration.times(246).div(30)
                "跳繩" -> it.burnFat = it.duration.times(252).div(30)
                "戶外體操" -> it.burnFat = it.duration.times(93).div(30)
                "步行" -> it.burnFat = it.duration.times(105).div(30)
                "桌球" -> it.burnFat = it.duration.times(126).div(30)
                "足球" -> it.burnFat = it.duration.times(231).div(30)
                "衝浪" -> it.burnFat = it.duration.times(216).div(30)
                "游泳" -> it.burnFat = it.duration.times(189).div(30)
                "TABATA" -> it.burnFat = it.duration.times(450).div(30)
                "撞球" -> it.burnFat = it.duration.times(45).div(30)
                "網球" -> it.burnFat = it.duration.times(198).div(30)
                "排球" -> it.burnFat = it.duration.times(108).div(30)
                "健走" -> it.burnFat = it.duration.times(165).div(30)
                "瑜珈" -> it.burnFat = it.duration.times(90).div(30)
                "有氧舞蹈" -> it.burnFat = it.duration.times(204).div(30)
                "飛輪" -> it.burnFat = it.duration.times(250).div(30)
                "高爾夫" -> it.burnFat = it.duration.times(150).div(30)
                "划船" -> it.burnFat = it.duration.times(132).div(30)
                "滑雪" -> it.burnFat = it.duration.times(216).div(30)
                "拳擊" -> it.burnFat = it.duration.times(342).div(30)
            }
        }
    }

    fun insertValue(recordImage: String) {
        addCardioRecordd.value?.let {
        it.recordImage = recordImage
        it.name = cardioItem.value?.cardio_title.toString()
        uploadCardioRecordData(it)
        }

    }

    fun uploadCardioStatusRecord() {
        _addCardioRecordd.value?.let {
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
