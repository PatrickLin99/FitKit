package com.patrick.fittracker.profile.edit

import android.util.Log
import androidx.databinding.InverseMethod
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patrick.fittracker.FitTrackerApplication
import com.patrick.fittracker.R
import com.patrick.fittracker.UserManger
import com.patrick.fittracker.data.Result
import com.patrick.fittracker.data.User
import com.patrick.fittracker.data.UserProfile
import com.patrick.fittracker.data.source.FitTrackerRepository
import com.patrick.fittracker.group.MuscleGroupTypeFilter
import com.patrick.fittracker.network.LoadApiStatus
import com.patrick.fittracker.profile.ProfileAvatarOutlineProvider
import com.patrick.fittracker.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EditProfileViewModel(private val repository: FitTrackerRepository) : ViewModel() {

    private val _addUserInfo = MutableLiveData<User>().apply {
        value = User( userProfile = UserProfile())
    }

    val addUserInfo: LiveData<User>
        get() = _addUserInfo

    private val _add = MutableLiveData<User>()

    val add: LiveData<User>
        get() = _add

    val outlineProvider = ProfileAvatarOutlineProvider()

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

        getLoginInfoResult()
    }

    fun userValueInsert(userHeight: Double, userWeight: Double, userBodyFat: Long) {
        addUserInfo.value?.let { userInfo ->
            userInfo.email = "${UserManger.userEmail}"
            userInfo.name = "${UserManger.userName}"
            userInfo.createdTime = UserManger.userData.createdTime
            userInfo.id = UserManger.userData.id

            userInfo.userProfile?.let { userProfile ->
                userProfile.info_height = userHeight.toLong()
                userProfile.info_weight = userWeight.toLong()
                userProfile.info_bodyFat = userBodyFat
                userProfile.info_BMI = userWeight.times(10000).div(userHeight * userHeight).toLong()
            }
        }
        addUserInfo.value?.let { uploadProfileInfo(user = it) }
    }

    private fun uploadProfileInfo(user: User) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.addUserInfo(user)) {
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

    private fun getLoginInfoResult() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getLoginInfo()

            _addUserInfo.value = when (result) {
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
