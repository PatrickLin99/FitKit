package com.patrick.fittracker.data.source

import androidx.lifecycle.MutableLiveData
import com.patrick.fittracker.data.*
import com.patrick.fittracker.group.MuscleGroupTypeFilter
import com.patrick.fittracker.record.selftraining.SetOrderFilter

interface FitTrackerDataSource {

    suspend fun getSelectedMuscleGroupMenu (group: MuscleGroupTypeFilter): Result<SelectedMuscleGroup>

    suspend fun getSetOrderNum (group: SetOrderFilter): Result<RecordSetOrder>

    suspend fun getCardioSelection (): Result<List<Cardio>>

    suspend fun getClassOption (): Result<List<ClassOption>>

    suspend fun addRecord(addTrainingRecord: AddTrainingRecord): Result<Boolean>

//     fun getLiveRecord(): MutableLiveData<List<AddTrainingRecord>>

    suspend fun getRecord(muscleKey: String): Result<List<AddTrainingRecord>>

    suspend fun addClassRecord(addTrainingRecord: AddTrainingRecord): Result<Boolean>

    suspend fun getClassRecord(classKey: String): Result<List<AddTrainingRecord>>

    suspend fun addCardioRecord(cardioRecord: CardioRecord): Result<Boolean>

    suspend fun addUserInfo(user: User): Result<Boolean>

    suspend fun addProfileInfo (userProfile: UserProfile): Result<Boolean>

    suspend fun getProfileInfo (userProfile: UserProfile): Result<List<UserProfile>>

    suspend fun addRecordTest(insertRecord: InsertRecord): Result<Boolean>

    suspend fun getTrainingRecord(): Result<List<InsertRecord>>

    suspend fun getLoginInfo(): Result<User>






//    suspend fun login(id: String): Result<Author>
//
//    suspend fun getArticles(): Result<List<Article>>
//
//    fun getLiveArticles(): MutableLiveData<List<Article>>
//
//    suspend fun publish(article: Article): Result<Boolean>
//
//    suspend fun delete(article: Article): Result<Boolean>
}