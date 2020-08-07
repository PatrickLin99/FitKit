package com.patrick.fittracker.data.source

import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.util.MultiClassKey
import com.patrick.fittracker.data.*
import com.patrick.fittracker.group.MuscleGroupTypeFilter
import com.patrick.fittracker.network.FitTrackerAipService
import com.patrick.fittracker.record.selftraining.SetOrderFilter

interface FitTrackerRepository {

    suspend fun getSelectedMuscleGroupMenu (group:MuscleGroupTypeFilter): Result<SelectedMuscleGroup>

    suspend fun getCardioSelection (): Result<List<Cardio>>

    suspend fun getClassOption (): Result<List<ClassOption>>

    suspend fun addCardioRecord(cardioRecord: CardioRecord): Result<Boolean>

    suspend fun addUserInfo(user: User): Result<Boolean>

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












//    suspend fun loginMockData(id: String): Result<Author>
//
//    suspend fun getArticles(): Result<List<Article>>
//
//    fun getLiveArticles(): MutableLiveData<List<Article>>
//
//    suspend fun publish(article: Article): Result<Boolean>
//
//    suspend fun delete(article: Article): Result<Boolean>
}