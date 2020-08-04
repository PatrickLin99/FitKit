package com.patrick.fittracker.data.source

import androidx.lifecycle.MutableLiveData
import com.patrick.fittracker.data.*
import com.patrick.fittracker.group.MuscleGroupTypeFilter
import com.patrick.fittracker.network.FitTrackerAipService
import com.patrick.fittracker.record.selftraining.SetOrderFilter

interface FitTrackerDataSource {

    suspend fun getSelectedMuscleGroupMenu (group: MuscleGroupTypeFilter): Result<SelectedMuscleGroup>

    suspend fun getSetOrderNum (group: SetOrderFilter): Result<RecordSetOrder>

    suspend fun getCardioSelection (): Result<List<Cardio>>

    suspend fun getClassOption (): Result<List<ClassOption>>

    suspend fun addCardioRecord(cardioRecord: CardioRecord): Result<Boolean>

    suspend fun addUserInfo(user: User): Result<Boolean>

    suspend fun addProfileInfo (userProfile: UserProfile): Result<Boolean>

    suspend fun getProfileInfo (userProfile: UserProfile): Result<List<UserProfile>>

    suspend fun addSelfRecord(insertRecord: InsertRecord): Result<Boolean>

    suspend fun getTrainingRecord(): Result<List<InsertRecord>>

    suspend fun getTrainingCardioRecord(): Result<List<CardioRecord>>

    suspend fun getLoginInfo(): Result<User>

    suspend fun getWeightTrainRecord(record: String): Result<List<InsertRecord>>

    suspend fun getCardioTrainRecord(record: String): Result<List<CardioRecord>>

    suspend fun getCalendarTrainingRecord(calendar: Long, endcalendar: Long): Result<List<InsertRecord>>

    suspend fun getCalendarTrainingCardioRecord(calendar: Long, endcalendar: Long): Result<List<CardioRecord>>

    suspend fun getLocationInfo(): Result<GymLocation>

    suspend fun getLocationList(key: String, location: String, radius: Int, language: String, keyword: String): Result<GymLocationListResult>

}