package com.patrick.fittracker.data.source

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

    override suspend fun getSetOrderNum(group: SetOrderFilter): Result<RecordSetOrder> {
        return remoteDataSource.getSetOrderNum(group)
    }

    override suspend fun getCardioSelection(): Result<List<Cardio>> {
        return remoteDataSource.getCardioSelection()
    }

    override suspend fun getClassOption(): Result<List<ClassOption>> {
        return remoteDataSource.getClassOption()
    }

    override suspend fun addRecord(addTrainingRecord: AddTrainingRecord): Result<Boolean> {
        return remoteDataSource.addRecord(addTrainingRecord)
    }

//    override fun getLiveRecord(): MutableLiveData<List<AddTrainingRecord>> {
//        return  remoteDataSource.getLiveRecord()
//    }

    override suspend fun getRecord(muscleKey: String): Result<List<AddTrainingRecord>> {
        return remoteDataSource.getRecord(muscleKey)
    }

    override suspend fun addClassRecord(addTrainingRecord: AddTrainingRecord): Result<Boolean> {
        return remoteDataSource.addClassRecord(addTrainingRecord)
    }

    override suspend fun getClassRecord(classKey: String): Result<List<AddTrainingRecord>> {
        return remoteDataSource.getClassRecord(classKey)
    }

    override suspend fun addCardioRecord(cardioRecord: CardioRecord): Result<Boolean> {
        return remoteDataSource.addCardioRecord(cardioRecord)
    }

    override suspend fun addUserInfo(user: User): Result<Boolean> {
        return remoteDataSource.addUserInfo(user)
    }

    override suspend fun addProfileInfo(userProfile: UserProfile): Result<Boolean> {
        return remoteDataSource.addProfileInfo(userProfile)
    }

    override suspend fun getProfileInfo(userProfile: UserProfile): Result<List<UserProfile>> {
        return remoteDataSource.getProfileInfo(userProfile)
    }

    override suspend fun addRecordTest(insertRecord: InsertRecord): Result<Boolean> {
        return remoteDataSource.addRecordTest(insertRecord)
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
}