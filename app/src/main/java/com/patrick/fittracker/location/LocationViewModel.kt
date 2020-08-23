package com.patrick.fittracker.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.patrick.fittracker.FitTrackerApplication
import com.patrick.fittracker.R
import com.patrick.fittracker.data.*
import com.patrick.fittracker.data.source.FitTrackerRepository
import com.patrick.fittracker.data.source.remote.FitTrackerRemoteDataSource.getLocationList
import com.patrick.fittracker.network.FitTrackerAipService
import com.patrick.fittracker.network.LoadApiStatus
import com.patrick.fittracker.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LocationViewModel(private val repository: FitTrackerRepository) : ViewModel() {

    private val _locationInfo = MutableLiveData<GymLocation>()

    val locationInfo: LiveData<GymLocation>
        get() = _locationInfo

    private val _gymList = MutableLiveData<GymLocationListResult>()

    val gymList: MutableLiveData<GymLocationListResult>
        get() = _gymList

    private val _detatilResult = MutableLiveData<List<DetailResults>>()

    val detailResult: MutableLiveData<List<DetailResults>>
        get() = _detatilResult

    // it for location list position design
    private val _snapPosition = MutableLiveData<Int>()

    val snapPosition: LiveData<Int>
        get() = _snapPosition

    val locationLat: ArrayList<String> = ArrayList()
    val locationLng: ArrayList<String> = ArrayList()
    val locationTitle: ArrayList<String> = ArrayList()

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

    fun getLocationResult() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getLocationInfo()

            _locationInfo.value = when (result) {
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
                    _error.value =
                        FitTrackerApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            _refreshStatus.value = false
        }
    }

    fun getLocationListResult(
        key: String,
        location: String,
        radius: Int,
        language: String,
        keyword: String
    ) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getLocationList(key, location, radius, language, keyword)

            _gymList.value = when (result) {

                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    _detatilResult.value = result.data.results
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
                    _error.value = ("R.string.you_know_nothing")
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
        }
    }

    fun insertMarkerValue() {
        val locationSize = gymList.value?.results?.size?.minus(1) ?: 0
        for (i in 0..locationSize) {
            gymList.value?.results.let {
                (it?.get(i)?.geometry?.location?.lat?.toDouble())?.let { it1 ->
                    it[i].geometry?.location?.lng?.toDouble()
                        ?.let { it2 ->
                            LatLng(
                                it1, it2
                            )
                        }
                }?.let { it2 ->
                    MarkerOptions().position(it2)
                        .title(it[i].name)
                }
                it?.get(i)?.geometry?.location?.lat?.let { lat -> locationLat.add(lat) }
                it?.get(i)?.geometry?.location?.lng?.let { lng -> locationLng.add(lng) }
                it?.get(i)?.name?.let { name -> locationTitle.add(name) }
            }

        }
    }

    fun onGalleryScrollChange(
        layoutManager: RecyclerView.LayoutManager?,
        linearSnapHelper: LinearSnapHelper
    ) {
        val snapView = linearSnapHelper.findSnapView(layoutManager)
        snapView?.let {
            layoutManager?.getPosition(snapView)?.let {
                if (it != snapPosition.value) {
                    _snapPosition.value = it
                }
            }
        }
    }
}
