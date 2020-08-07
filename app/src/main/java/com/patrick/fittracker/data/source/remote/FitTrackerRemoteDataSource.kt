package com.patrick.fittracker.data.source.remote


import android.icu.util.Calendar
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.patrick.fittracker.FitTrackerApplication
import com.patrick.fittracker.R
import com.patrick.fittracker.UserManger
import com.patrick.fittracker.data.*
import com.patrick.fittracker.data.source.FitTrackerDataSource
import com.patrick.fittracker.group.MuscleGroupTypeFilter
import com.patrick.fittracker.network.FitTrackerApi
import com.patrick.fittracker.util.Logger
import com.patrick.fittracker.util.Util.isInternetConnected
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object FitTrackerRemoteDataSource : FitTrackerDataSource {

    private const val PATH_ARTICLES_MUSCLE_GROUP = "muscle_group"
    private const val PATH_ARTICLES_CARDIO = "cardio"
    private const val PATH_ARTICLES_CLASS_OPTION = "class_option"
    private const val PATH_ARTICLES_USER = "users"
    private const val PATH_ARTICLES_GYM_LOCATION = "gymlist"
    private const val PATH_ARTICLES_GYM_MENU = "menu"
    private const val PATH_ARTICLES_GYM_SELF = "self"
    private const val PATH_ARTICLES_GYM_RECORD = "record"
    private const val PATH_ARTICLES_CREATED_TIME = "createdTime"


    override suspend fun getSelectedMuscleGroupMenu(group: MuscleGroupTypeFilter): Result<SelectedMuscleGroup> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(PATH_ARTICLES_MUSCLE_GROUP)
                .document(group.value)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val selectedMuscleGroup =
                            task.result?.toObject(SelectedMuscleGroup::class.java)

                        selectedMuscleGroup?.let {

                            continuation.resume(Result.Success(it))

                        }

                    } else {
                        task.exception?.let {
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(FitTrackerApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }

    override suspend fun getCardioSelection(): Result<List<Cardio>> =
        suspendCoroutine { continuation ->

            FirebaseFirestore.getInstance()
                .collection(PATH_ARTICLES_CARDIO)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val list = mutableListOf<Cardio>()

                        task.result?.forEach { document ->
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

    override suspend fun getClassOption(): Result<List<ClassOption>> =
        suspendCoroutine { continuation ->

            FirebaseFirestore.getInstance()
                .collection(PATH_ARTICLES_CLASS_OPTION)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val list = mutableListOf<ClassOption>()
                        task.result?.forEach { document ->
                            Logger.d(document.id + " => " + document.data)

                            val classOption = document.toObject(ClassOption::class.java)
                            list.add(classOption)
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

    override suspend fun addSelfRecord(insertRecord: InsertRecord): Result<Boolean> =
        suspendCoroutine { continuation ->

            val user = FirebaseFirestore.getInstance().collection(PATH_ARTICLES_USER)
            val document = user.document("${UserManger.userID}")

            insertRecord.createdTime = Calendar.getInstance().timeInMillis

            document
                .collection(PATH_ARTICLES_GYM_MENU)
                .document(PATH_ARTICLES_GYM_SELF)
                .collection(PATH_ARTICLES_GYM_RECORD)
                .add(insertRecord)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Logger.i("FitTracker: $insertRecord")

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

    override suspend fun addCardioRecord(cardioRecord: CardioRecord): Result<Boolean> =
        suspendCoroutine { continuation ->

            val user = FirebaseFirestore.getInstance().collection(PATH_ARTICLES_USER)
            val document = user.document("${UserManger.userID}")

            cardioRecord.createdTime = Calendar.getInstance().timeInMillis

            document
                .collection(PATH_ARTICLES_CARDIO)
                .document(PATH_ARTICLES_GYM_SELF)
                .collection(PATH_ARTICLES_GYM_RECORD)
                .add(cardioRecord)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Logger.i("FitTracker: $cardioRecord")

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

    override suspend fun addUserInfo(user: User): Result<Boolean> =
        suspendCoroutine { continuation ->

            val users = FirebaseFirestore.getInstance().collection(PATH_ARTICLES_USER)
            val document = users.document("${UserManger.userID}")

            user.id = document.id
            user.createdTime = Calendar.getInstance().timeInMillis

            users
                .whereEqualTo("id", "${UserManger.userID}")
                .get()
                .addOnSuccessListener { task ->
                    if (task.isEmpty) {

                        document
                            .set(user)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Logger.i("FitTracker: $user")

//                                UserManger.userData.id = document.id

                                    continuation.resume(Result.Success(true))
                                } else {
                                    task.exception?.let {

                                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                                        continuation.resume(Result.Error(it))
                                        return@addOnCompleteListener
                                    }
                                    continuation.resume(
                                        Result.Fail(
                                            FitTrackerApplication.instance.getString(
                                                R.string.you_know_nothing
                                            )
                                        )
                                    )
                                }
                            }
                    } else {

                        //若已註冊但又按編輯資料則是覆蓋過去(身高體重等)
                        Logger.w("已註冊")
                        document
                            .set(user)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Logger.i("FitTracker: $user")

                                    continuation.resume(Result.Success(true))
                                } else {
                                    task.exception?.let {

                                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                                        continuation.resume(Result.Error(it))
                                        return@addOnCompleteListener
                                    }
                                    continuation.resume(
                                        Result.Fail(
                                            FitTrackerApplication.instance.getString(
                                                R.string.you_know_nothing
                                            )
                                        )
                                    )
                                }
                            }
                    }
                }

        }

    override suspend fun getTrainingRecord(): Result<List<InsertRecord>> =
        suspendCoroutine { continuation ->

            FirebaseFirestore.getInstance()
                .collection(PATH_ARTICLES_USER)
                .document("${UserManger.userID}")
                .collection(PATH_ARTICLES_GYM_MENU)
                .document(PATH_ARTICLES_GYM_SELF)
                .collection(PATH_ARTICLES_GYM_RECORD)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<InsertRecord>()
                        task.result?.forEach { document ->
                            Logger.d(document.id + " => " + document.data)

                            val insertRecord = document.toObject(InsertRecord::class.java)
                            list.add(insertRecord)
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

    override suspend fun getTrainingCardioRecord(): Result<List<CardioRecord>> =
        suspendCoroutine { continuation ->

            var timeNow: Long = Calendar.getInstance().timeInMillis

            FirebaseFirestore.getInstance()
                .collection(PATH_ARTICLES_USER)
                .document("${UserManger.userID}")
                .collection(PATH_ARTICLES_CARDIO)
                .document(PATH_ARTICLES_GYM_SELF)
                .collection(PATH_ARTICLES_GYM_RECORD)
                .orderBy(PATH_ARTICLES_CREATED_TIME, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<CardioRecord>()
                        task.result?.forEach { document ->
                            Logger.d(document.id + " => " + document.data)

                            val cardioRecord = document.toObject(CardioRecord::class.java)
                            list.add(cardioRecord)
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

    override suspend fun getLoginInfo(): Result<User> = suspendCoroutine { continuation ->

        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLES_USER)
            .whereEqualTo("id", "${UserManger.userID}")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var user1 = User()
                    for (document in task.result!!) {

                        Logger.d(document.id + " => " + document.data)

                        val user = document.toObject(User::class.java)
                        user1 = user
                    }
                    continuation.resume(Result.Success(user1))
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

    override suspend fun getWeightTrainRecord(record: String): Result<List<InsertRecord>> =
        suspendCoroutine { continuation ->

            FirebaseFirestore.getInstance()
                .collection(PATH_ARTICLES_USER)
                .document("${UserManger.userID}")
                .collection(PATH_ARTICLES_GYM_MENU)
                .document(PATH_ARTICLES_GYM_SELF)
                .collection(PATH_ARTICLES_GYM_RECORD)
                .whereEqualTo("name", "$record")
                .orderBy(PATH_ARTICLES_CREATED_TIME, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<InsertRecord>()
                        task.result?.forEach { document ->
                            Logger.d(document.id + " => " + document.data)

                            val insertRecord = document.toObject(InsertRecord::class.java)
                            list.add(insertRecord)
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

    override suspend fun getCardioTrainRecord(recordKey: String): Result<List<CardioRecord>> =
        suspendCoroutine { continuation ->

            FirebaseFirestore.getInstance()
                .collection(PATH_ARTICLES_USER)
                .document("${UserManger.userID}")
                .collection(PATH_ARTICLES_CARDIO)
                .document(PATH_ARTICLES_GYM_SELF)
                .collection(PATH_ARTICLES_GYM_RECORD)
                .whereEqualTo("name", "$recordKey")
                .orderBy(PATH_ARTICLES_CREATED_TIME, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<CardioRecord>()
                        for (document in task.result!!) {

                            Logger.d(document.id + " => " + document.data)

                            val cardioRecord = document.toObject(CardioRecord::class.java)
                            list.add(cardioRecord)
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

    override suspend fun getCalendarTrainingRecord(
        calendar: Long,
        endcalendar: Long
    ): Result<List<InsertRecord>> = suspendCoroutine { continuation ->

        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLES_USER)
            .document("${UserManger.userID}")
            .collection(PATH_ARTICLES_GYM_MENU)
            .document(PATH_ARTICLES_GYM_SELF)
            .collection(PATH_ARTICLES_GYM_RECORD)
            .whereGreaterThan(PATH_ARTICLES_CREATED_TIME, calendar)
            .whereLessThan(PATH_ARTICLES_CREATED_TIME, endcalendar)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<InsertRecord>()
                    task.result?.forEach { document ->
                        Logger.d(document.id + " => " + document.data)

                        val inserRecord = document.toObject(InsertRecord::class.java)
                        list.add(inserRecord)
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

    override suspend fun getCalendarTrainingCardioRecord(
        calendar: Long,
        endcalendar: Long
    ): Result<List<CardioRecord>> = suspendCoroutine { continuation ->

        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLES_USER)
            .document("${UserManger.userID}")
            .collection("cardio")
            .document("self")
            .collection("record")
            .whereGreaterThan("createdTime", calendar)
            .whereLessThan("createdTime", endcalendar)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<CardioRecord>()
                    task.result?.forEach { document ->
                        Logger.d(document.id + " => " + document.data)

                        val cardioRecord = document.toObject(CardioRecord::class.java)
                        list.add(cardioRecord)
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

    override suspend fun getLocationInfo(): Result<GymLocation> = suspendCoroutine { continuation ->

        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLES_GYM_LOCATION)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var gymLocation1 = GymLocation()
                    for (document in task.result!!) {

                        Logger.d(document.id + " => " + document.data)

                        val gymLocation = document.toObject(GymLocation::class.java)
                        gymLocation1 = gymLocation
                    }
                    continuation.resume(Result.Success(gymLocation1))
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

    override suspend fun getLocationList(
        key: String,
        location: String,
        radius: Int,
        language: String,
        keyword: String
    ): Result<GymLocationListResult> {

        if (!isInternetConnected()) {
            return Result.Fail("internet_not_connected")
        }

        return try {
            // this will run on a thread managed by Retrofit
            val listResult = FitTrackerApi.retrofitService.getLocationList(
                key = key,
                location = location,
                radius = radius,
                language = language
            )

            listResult.error?.let {
                return Result.Fail(it)
            }
            Result.Success(listResult)

        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            Result.Error(e)
        }
    }

}