package com.patrick.fittracker.data.source.remote


import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.patrick.fittracker.FitTrackerApplication
import com.patrick.fittracker.R
import com.patrick.fittracker.data.*
import com.patrick.fittracker.data.source.FitTrackerDataSource
import com.patrick.fittracker.group.MuscleGroupTypeFilter
import com.patrick.fittracker.record.selftraining.SetOrderFilter
import com.patrick.fittracker.util.Logger
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object FitTrackerRemoteDataSource : FitTrackerDataSource {

    private const val PATH_ARTICLES = "articles"
    private const val KEY_CREATED_TIME = "createdTime"
    private const val PATH_ARTICLES_MUSCLE_GROUP = "muscle_group"
    private const val PATH_ARTICLES_NUM = "record_set_order"
    private const val PATH_ARTICLES_CARDIO = "cardio"
    private const val PATH_ARTICLES_CLASS_OPTION = "class_option"
    private const val PATH_ARTICLES_USER = "users"


    override suspend fun getSelectedMuscleGroupMenu(group: MuscleGroupTypeFilter): Result<SelectedMuscleGroup> = suspendCoroutine { continuation ->

        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLES_MUSCLE_GROUP)
            .document(group.value)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val selectedMuscleGroup = task.result?.toObject(SelectedMuscleGroup::class.java)

                    selectedMuscleGroup?.let {

                        continuation.resume(Result.Success(it))

                    }

                } else {
                    task.exception?.let {

//                        Log.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(FitTrackerApplication.instance.getString(R.string.you_know_nothing)))
                }
            }

    }

    override suspend fun getSetOrderNum(group: SetOrderFilter): Result<RecordSetOrder>  = suspendCoroutine { continuation ->

        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLES_NUM)
            .document(group.value)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val orderNum = task.result?.toObject(RecordSetOrder::class.java)

                    orderNum?.let {

                        continuation.resume(Result.Success(it))

                    }

                } else {
                    task.exception?.let {

//                        Log.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(FitTrackerApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
        }

    override suspend fun getCardioSelection(): Result<List<Cardio>> = suspendCoroutine { continuation ->

        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLES_CARDIO)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Cardio>()
                    for (document in task.result!!) {
                        Logger.d(document.id + " => " + document.data)

                        val cardio = document.toObject(Cardio::class.java)
                        list.add(cardio)
                    }
                    continuation.resume(Result.Success(list))
                } else {
                    task.exception?.let {

                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(FitTrackerApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }

    override suspend fun getClassOption(): Result<List<ClassOption>> = suspendCoroutine { continuation ->

        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLES_CLASS_OPTION)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<ClassOption>()
                    for (document in task.result!!) {
                        Logger.d(document.id + " => " + document.data)

                        val classoption = document.toObject(ClassOption::class.java)
                        list.add(classoption)
                    }
                    continuation.resume(Result.Success(list))
                } else {
                    task.exception?.let {

                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(FitTrackerApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }

    override suspend fun addRecord(addTrainingRecord: AddTrainingRecord): Result<Boolean> = suspendCoroutine { continuation ->

        val user = FirebaseFirestore.getInstance().collection(PATH_ARTICLES_USER)
        val document = user.document()

        addTrainingRecord.id = document.id
        addTrainingRecord.createdTime = Calendar.getInstance().timeInMillis

        document
            .set(addTrainingRecord)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Logger.i("FitTracker: $addTrainingRecord")

                    continuation.resume(Result.Success(true))
                } else {
                    task.exception?.let {

                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(FitTrackerApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }

    override suspend fun getRecord(muscleKey: String): Result<List<AddTrainingRecord>> = suspendCoroutine { continuation ->

        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLES_USER)
            .whereEqualTo("category_title", muscleKey)
//            .whereEqualTo("createdTime","${Calendar.getInstance().timeInMillis} + 600000")
            .orderBy("order_title",Query.Direction.ASCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<AddTrainingRecord>()
                    for (document in task.result!!) {
                        Logger.d(document.id + " => " + document.data)

                        val add = document.toObject(AddTrainingRecord::class.java)
                        list.add(add)
                    }
                    continuation.resume(Result.Success(list))
                } else {
                    task.exception?.let {

                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(FitTrackerApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }

    override suspend fun addClassRecord(addTrainingRecord: AddTrainingRecord): Result<Boolean> = suspendCoroutine { continuation ->

        val user = FirebaseFirestore.getInstance().collection(PATH_ARTICLES_USER)
        val document = user.document()

        addTrainingRecord.id = document.id
        addTrainingRecord.createdTime = Calendar.getInstance().timeInMillis

        document
            .set(addTrainingRecord)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Logger.i("FitTracker: $addTrainingRecord")

                    continuation.resume(Result.Success(true))
                } else {
                    task.exception?.let {

                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(FitTrackerApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }

    override suspend fun getClassRecord(classKey: String): Result<List<AddTrainingRecord>> = suspendCoroutine { continuation ->

        var timeNow : Long = Calendar.getInstance().timeInMillis
        var timePeriod : Long = timeNow + 600000

        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLES_USER)
            .whereEqualTo("category_title", classKey)
//            .orderBy("createdTime")
//            .whereGreaterThan("createdTime",timeNow)
//            .whereLessThan("createdTime",timePeriod)
            .orderBy("order_title",Query.Direction.ASCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<AddTrainingRecord>()
                    for (document in task.result!!) {
                        Logger.d(document.id + " => " + document.data)

                        val add = document.toObject(AddTrainingRecord::class.java)
                        list.add(add)
                    }
                    continuation.resume(Result.Success(list))
                } else {
                    task.exception?.let {

                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(FitTrackerApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }

    override suspend fun addCardioRecord(addTrainingRecord: AddTrainingRecord): Result<Boolean> = suspendCoroutine { continuation ->

        val user = FirebaseFirestore.getInstance().collection(PATH_ARTICLES_USER)
        val document = user.document()

        addTrainingRecord.id = document.id
        addTrainingRecord.createdTime = Calendar.getInstance().timeInMillis

        document
            .set(addTrainingRecord)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Logger.i("FitTracker: $addTrainingRecord")

                    continuation.resume(Result.Success(true))
                } else {
                    task.exception?.let {

                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(FitTrackerApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }


}