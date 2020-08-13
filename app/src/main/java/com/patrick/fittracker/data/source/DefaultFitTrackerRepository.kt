package com.patrick.fittracker.data.source

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.patrick.fittracker.data.*
import com.patrick.fittracker.group.MuscleGroupTypeFilter
import com.patrick.fittracker.network.FitTrackerAipService
import com.patrick.fittracker.record.selftraining.SetOrderFilter

class DefaultFitTrackerRepository(private val remoteDataSource: FitTrackerDataSource,
                                  private val localDataSource: FitTrackerDataSource
) : FitTrackerRepository {
    override suspend fun getSelectedMuscleGroupMenu(group: MuscleGroupTypeFilter): Result<SelectedMuscleGroup> {
        return remoteDataSource.getSelectedMuscleGroupMenu(group)
    }

    override suspend fun getCardioSelection(): Result<List<Cardio>> {
        return remoteDataSource.getCardioSelection()
    }

    override suspend fun getClassOption(): Result<List<ClassOption>> {
        return remoteDataSource.getClassOption()
    }

    override suspend fun addCardioRecord(cardioRecord: CardioRecord): Result<Boolean> {
        return remoteDataSource.addCardioRecord(cardioRecord)
    }

    override suspend fun addUserInfo(user: User): Result<Boolean> {
        return remoteDataSource.addUserInfo(user)
    }

    override suspend fun addSelfRecord(insertRecord: InsertRecord): Result<Boolean> {
        return remoteDataSource.addSelfRecord(insertRecord)
    }

    override suspend fun getTrainingRecord(): Result<List<InsertRecord>> {
        return remoteDataSource.getTrainingRecord()
    }

    override suspend fun getLoginInfo(): Result<User> {
        return remoteDataSource.getLoginInfo()
    }

    override suspend fun getWeightTrainRecord(record: String): Result<List<InsertRecord>> {
        return remoteDataSource.getWeightTrainRecord(record)
    }

    override suspend fun getTrainingCardioRecord(): Result<List<CardioRecord>> {
        return remoteDataSource.getTrainingCardioRecord()
    }

    override suspend fun getCardioTrainRecord(record: String): Result<List<CardioRecord>> {
        return remoteDataSource.getCardioTrainRecord(record)
    }

    override suspend fun getCalendarTrainingRecord(calendar: Long, endcalendar: Long): Result<List<InsertRecord>> {
        return remoteDataSource.getCalendarTrainingRecord(calendar, endcalendar)
    }

    override suspend fun getCalendarTrainingCardioRecord(calendar: Long, endcalendar: Long): Result<List<CardioRecord>> {
        return remoteDataSource.getCalendarTrainingCardioRecord(calendar, endcalendar)
    }

    override suspend fun getLocationInfo(): Result<GymLocation> {
        return  remoteDataSource.getLocationInfo()
    }

    //Google Place Nearby API
    override suspend fun getLocationList(
        key: String,
        location: String,
        radius: Int,
        language: String,
        keyword: String
    ): Result<GymLocationListResult> {
        return remoteDataSource.getLocationList(key, location, radius, language, keyword)
    }

    override suspend fun addSelfTrainingImage(uri: Uri): Result<String> {
        return remoteDataSource.addCardioImage(uri)
    }

    override suspend fun addClassOptionImage(uri: Uri): Result<String> {
        return remoteDataSource.addCardioImage(uri)
    }

    override suspend fun addCardioImage(uri: Uri): Result<String> {
        return remoteDataSource.addCardioImage(uri)
    }
}