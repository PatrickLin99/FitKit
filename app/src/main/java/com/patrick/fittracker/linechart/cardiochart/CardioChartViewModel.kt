package com.patrick.fittracker.linechart.cardiochart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.patrick.fittracker.FitTrackerApplication
import com.patrick.fittracker.R
import com.patrick.fittracker.TimeUtil
import com.patrick.fittracker.data.CardioRecord
import com.patrick.fittracker.data.FitDetail
import com.patrick.fittracker.data.InsertRecord
import com.patrick.fittracker.data.Result
import com.patrick.fittracker.data.source.FitTrackerRepository
import com.patrick.fittracker.network.LoadApiStatus
import com.patrick.fittracker.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class CardioChartViewModel(private val repository: FitTrackerRepository,
                           private val recordKey: CardioRecord
) : ViewModel() {

    private val _record = MutableLiveData<List<CardioRecord>>()

    val record: LiveData<List<CardioRecord>>
        get() = _record

    val entriesDuration: MutableList<Entry> = ArrayList()
    val entriesCalories: MutableList<Entry> = ArrayList()
    val labels : ArrayList<String> = ArrayList()

//--------------------------------------------------------------------------------------------------

    private val _leave = MutableLiveData<Boolean>()

    val leave: LiveData<Boolean>
        get() = _leave

    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    private var viewModelJob = Job()

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

    fun insertChartValue() {
        record.value?.let {
            val cardioListSizeStart: Int = if (it.size > 10) {
                it.size.minus(10)
            } else {
                0
            }

            for (i in cardioListSizeStart until it.size) {
                it[i].duration.toFloat()
                    .let { order ->
                        Entry(i.toFloat(), order)
                    }.let { pointValue -> entriesDuration.add(pointValue) }

                it[i].burnFat.toFloat()
                    .let { order ->
                        Entry(i.toFloat(), order)
                    }.let { pointValue -> entriesCalories.add(pointValue) }

                labels.add(TimeUtil.AnalysisStampToDate(it[i].createdTime, Locale.TAIWAN))
            }
        }
    }

    fun getCardioRecordRecordResult(recordKey: CardioRecord) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getCardioTrainRecord(recordKey.name)

            _record.value = when (result) {
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
        }
    }

    fun leave(needRefresh: Boolean = false) {
        _leave.value = needRefresh
    }

    fun onLeft() {
        _leave.value = null
    }
}
