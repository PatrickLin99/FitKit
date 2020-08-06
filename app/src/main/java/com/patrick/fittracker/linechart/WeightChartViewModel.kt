package com.patrick.fittracker.linechart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.patrick.fittracker.FitTrackerApplication
import com.patrick.fittracker.R
import com.patrick.fittracker.TimeUtil
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

class WeightChartViewModel(private val repository: FitTrackerRepository,
                           private val recordKey: InsertRecord
) : ViewModel() {

    private val _record = MutableLiveData<List<InsertRecord>>()

    val record: LiveData<List<InsertRecord>>
        get() = _record

    val entries: MutableList<Entry> = ArrayList()
    val labels: ArrayList<String> = ArrayList()

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
        record.value?.let { records ->
            val weightListSizeStart: Int = if (records.size > 10) {
                records.size.minus(10)
            } else {
                0
            }
            for (i in weightListSizeStart until records.size) {
                records.sortedBy { records[i].createdTime }[i].fitDetail.maxBy { fitDetail -> fitDetail.weight }?.weight?.toFloat()
                    ?.let { order -> Entry(i.toFloat(), order) }
                    ?.let { point -> entries.add(point) }
                labels.add(TimeUtil.AnalysisStampToDate(records[i].createdTime, Locale.TAIWAN))
            }
        }
    }

    fun getWeightRecordRecordResult(recordKey: InsertRecord) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getWeightTrainRecord(recordKey.name)

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
