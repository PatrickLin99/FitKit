package com.patrick.fittracker.data.source

import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.util.MultiClassKey
import com.patrick.fittracker.data.*
import com.patrick.fittracker.group.MuscleGroupTypeFilter
import com.patrick.fittracker.record.selftraining.SetOrderFilter

interface FitTrackerRepository {

    suspend fun getSelectedMuscleGroupMenu (group:MuscleGroupTypeFilter): Result<SelectedMuscleGroup>

    suspend fun getSetOrderNum (group: SetOrderFilter): Result<RecordSetOrder>

    suspend fun getCardioSelection (): Result<List<Cardio>>

    suspend fun getClassOption (): Result<List<ClassOption>>

    suspend fun addRecord(addTrainingRecord: AddTrainingRecord): Result<Boolean>

//     fun getLiveRecord(): MutableLiveData<List<AddTrainingRecord>>

    suspend fun getRecord(muscleKey: String): Result<List<AddTrainingRecord>>

    suspend fun addClassRecord(addTrainingRecord: AddTrainingRecord): Result<Boolean>

    suspend fun getClassRecord(classKey: String): Result<List<AddTrainingRecord>>

    suspend fun addCardioRecord(addTrainingRecord: AddTrainingRecord): Result<Boolean>




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