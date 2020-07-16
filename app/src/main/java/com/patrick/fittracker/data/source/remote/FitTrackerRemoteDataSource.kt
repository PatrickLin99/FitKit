package com.patrick.fittracker.data.source.remote


import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Timestamp.now
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.patrick.fittracker.FitTrackerApplication
import com.patrick.fittracker.R
import com.patrick.fittracker.UserManger
import com.patrick.fittracker.data.*
import com.patrick.fittracker.data.source.FitTrackerDataSource
import com.patrick.fittracker.group.MuscleGroupTypeFilter
import com.patrick.fittracker.record.selftraining.SetOrderFilter
import com.patrick.fittracker.util.Logger
import java.text.SimpleDateFormat
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

        var timeNow : Long = Calendar.getInstance().timeInMillis
        var timePeriod : Long = timeNow + 60000

        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLES_USER)
            .whereEqualTo("category_title", muscleKey)
//            .orderBy("createdTime", Query.Direction.ASCENDING)
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

    //------------------------Change Data Structure--------------------------------------------------------------------------------------------------------------------------

    override suspend fun addRecordTest (insertRecord: InsertRecord): Result<Boolean> = suspendCoroutine { continuation ->

        val user = FirebaseFirestore.getInstance().collection(PATH_ARTICLES_USER)
        val document = user.document("${UserManger.userID}")

//        insertRecord.id = document.id
//        insertRecord.createdTime = Calendar.getInstance().timeInMillis
        insertRecord.createdTime = Calendar.getInstance().timeInMillis


        document
            .collection("menu")
            .document("self")
            .collection("record")
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

//
//    override suspend fun getRecordTest(muscleKey: String): Result<List<AddTrainingRecord>> = suspendCoroutine { continuation ->
//
//        var timeNow : Long = Calendar.getInstance().timeInMillis
//        var timePeriod : Long = timeNow + 60000
//
//        FirebaseFirestore.getInstance()
//            .collection(PATH_ARTICLES_USER)
//            .whereEqualTo("category_title", muscleKey)
////            .orderBy("createdTime", Query.Direction.ASCENDING)
////            .whereLessThan("createdTime",timePeriod)
//            .orderBy("order_title",Query.Direction.ASCENDING)
//            .get()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val list = mutableListOf<AddTrainingRecord>()
//                    for (document in task.result!!) {
//                        Logger.d(document.id + " => " + document.data)
//
//                        val add = document.toObject(AddTrainingRecord::class.java)
//                        list.add(add)
//                    }
//                    continuation.resume(Result.Success(list))
//                } else {
//                    task.exception?.let {
//
//                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
//                        continuation.resume(Result.Error(it))
//                        return@addOnCompleteListener
//                    }
//                    continuation.resume(Result.Fail(FitTrackerApplication.instance.getString(R.string.you_know_nothing)))
//                }
//            }
//    }


    //------------------------------------------------------------------------------------------------------



    override suspend fun addClassRecord(addTrainingRecord: AddTrainingRecord): Result<Boolean> = suspendCoroutine { continuation ->

        val user = FirebaseFirestore.getInstance().collection(PATH_ARTICLES_USER)
        val document = user.document("0ZNaANujjSHrvQpGffaB")

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
        var timePeriod : Long = timeNow + 60000000000

        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLES_USER)
            .whereEqualTo("category_title", classKey)
            .orderBy("createdTime", Query.Direction.ASCENDING)
//            .whereGreaterThan("createdTime",timeNow)
            .whereLessThan("createdTime",timePeriod)
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

    override suspend fun addCardioRecord(cardioRecord: CardioRecord): Result<Boolean> = suspendCoroutine { continuation ->

        val user = FirebaseFirestore.getInstance().collection(PATH_ARTICLES_USER)
        val document = user.document("${UserManger.userID}")

//        cardioRecord.id = document.id
        cardioRecord.createdTime = Calendar.getInstance().timeInMillis

        document
            .collection("cardio")
            .document("self")
            .collection("record")
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

//    override suspend fun addUserInfo(user: User): Result<Boolean> = suspendCoroutine { continuation ->
//
//        val users = FirebaseFirestore.getInstance().collection(PATH_ARTICLES_USER)
//        val document = users.document("JJx43PDXIrF8tGF0fpcU")
//
//        user.id = document.id
//        user.createdTime = Calendar.getInstance().timeInMillis
//
//        document
//            .set(user)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Logger.i("FitTracker: $user")
//
//                    continuation.resume(Result.Success(true))
//                } else {
//                    task.exception?.let {
//
//                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
//                        continuation.resume(Result.Error(it))
//                        return@addOnCompleteListener
//                    }
//                    continuation.resume(Result.Fail(FitTrackerApplication.instance.getString(R.string.you_know_nothing)))
//                }
//            }
//    }

    override suspend fun addUserInfo(user: User): Result<Boolean> = suspendCoroutine { continuation ->

        val users = FirebaseFirestore.getInstance().collection(PATH_ARTICLES_USER)
        val document = users.document("${UserManger.userID}")


        user.id = document.id
        user.createdTime = Calendar.getInstance().timeInMillis

        users
            .whereEqualTo("id", "${UserManger.userID}")
            .get()
            .addOnSuccessListener {task ->
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
                    Log.d("aaaaaaaaa","${UserManger.userData.userProfile}")
                }
            }

    }

    override suspend fun addProfileInfo(userProfile: UserProfile): Result<Boolean>  = suspendCoroutine { continuation ->

        val user = FirebaseFirestore.getInstance().collection(PATH_ARTICLES_USER)
        val document = user.document("JJx43PDXIrF8tGF0fpcU")

        userProfile.id = document.id
        userProfile.createdTime = Calendar.getInstance().timeInMillis

        document
//            .collection("profileInfo")
//            .document("info")
            .set(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Logger.i("FitTracker: $userProfile")

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

    //As an Object
//    override suspend fun getTrainingRecord(): Result<InsertRecord> = suspendCoroutine { continuation ->
//
//        var timeNow: Long = Calendar.getInstance().timeInMillis
//        var timePeriod: Long = timeNow + 60000000000
//
//        FirebaseFirestore.getInstance()
//            .collection(PATH_ARTICLES_USER)
//            .document(UserManger.userData.id)
//            .collection("menu")
//            .document("self")
//            .collection("record")
//            .whereEqualTo("name","俯臥腿彎曲")
//            .get()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    var record = InsertRecord(fitDetail = listOf())
//                    for (document in task.result!!) {
//
//                        Logger.d(document.id + " => " + document.data)
//
//                        val inserRecord = document.toObject(InsertRecord::class.java)
//                        record = inserRecord
//                    }
//                    continuation.resume(Result.Success(record))
//                } else {
//                    task.exception?.let {
//
//                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
//                        continuation.resume(Result.Error(it))
//                        return@addOnCompleteListener
//                    }
//                    continuation.resume(Result.Fail(FitTrackerApplication.instance.getString(R.string.you_know_nothing)))
//                }
//            }
//    }

    //As a list
    override suspend fun getTrainingRecord(): Result<List<InsertRecord>> = suspendCoroutine { continuation ->

        var timeNow: Long = Calendar.getInstance().timeInMillis
        var timePeriod: Long = timeNow + 60000000000

        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLES_USER)
            .document("${UserManger.userID}")
            .collection("menu")
            .document("self")
            .collection("record")
//            .whereEqualTo("name","俯臥腿彎曲")
//            .whereGreaterThan("createdTime","1594644296569")
//            .whereLessThan("createdTime","1594649425984")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<InsertRecord>()
                    for (document in task.result!!) {

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

    override suspend fun getTrainingCardioRecord(): Result<List<CardioRecord>> = suspendCoroutine { continuation ->

        var timeNow: Long = Calendar.getInstance().timeInMillis
        var timePeriod: Long = timeNow + 60000000000

        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLES_USER)
            .document("${UserManger.userID}")
            .collection("cardio")
            .document("self")
            .collection("record")
//            .whereEqualTo("name","俯臥腿彎曲")
//            .whereGreaterThan("createdTime","1594644296569")
//            .whereLessThan("createdTime","1594649425984")
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

    override suspend fun getProfileInfo(userProfile: UserProfile): Result<List<UserProfile>>  = suspendCoroutine { continuation ->

//        var timeNow: Long = Calendar.getInstance().timeInMillis
//        var timePeriod: Long = timeNow + 60000000000
//
//        FirebaseFirestore.getInstance()
//            .collection(PATH_ARTICLES_USER)
//            .document("JJx43PDXIrF8tGF0fpcU")
//            .collection("profileInfo")
//            .document("info")
//            .get()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val list = mutableListOf<UserProfile>()
//                    for (document in task.result!!) {
//
//                        Logger.d(document.id + " => " + document.data)
//
//                        val add = document.toObject(UserProfile::class.java)
//                        list.add(add)
//                    }
//                    continuation.resume(Result.Success(list))
//                } else {
//                    task.exception?.let {
//
//                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
//                        continuation.resume(Result.Error(it))
//                        return@addOnCompleteListener
//                    }
//                    continuation.resume(Result.Fail(FitTrackerApplication.instance.getString(R.string.you_know_nothing)))
//                }
//            }
    }

    override suspend fun getLoginInfo(): Result<User> = suspendCoroutine { continuation ->

        var timeNow: Long = Calendar.getInstance().timeInMillis
        var timePeriod: Long = timeNow + 60000000000

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

    override suspend fun getWeightTrainRecord(recordKey: String): Result<List<InsertRecord>> = suspendCoroutine { continuation ->

        var timeNow: Long = Calendar.getInstance().timeInMillis
        var timePeriod: Long = timeNow + 60000000000

        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLES_USER)
            .document("${UserManger.userID}")
            .collection("menu")
            .document("self")
            .collection("record")
            .whereEqualTo("name","$recordKey")
            .orderBy("createdTime",Query.Direction.ASCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<InsertRecord>()
                    for (document in task.result!!) {

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

    override suspend fun getCardioTrainRecord(recordKey: String): Result<List<CardioRecord>> = suspendCoroutine { continuation ->

        var timeNow: Long = Calendar.getInstance().timeInMillis
        var timePeriod: Long = timeNow + 60000000000

        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLES_USER)
            .document("${UserManger.userID}")
            .collection("cardio")
            .document("self")
            .collection("record")
            .whereEqualTo("name","$recordKey")
            .orderBy("createdTime",Query.Direction.ASCENDING)
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

    override suspend fun getCalendarTrainingRecord(calendar: Long, endcalendar: Long): Result<List<InsertRecord>> = suspendCoroutine { continuation ->

        var timeNow: Long = Calendar.getInstance().timeInMillis
        var timePeriod: Long = timeNow + 60000000000

        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLES_USER)
            .document("${UserManger.userID}")
            .collection("menu")
            .document("self")
            .collection("record")
//            .whereEqualTo("createdTime",1594649462560)
//            .whereEqualTo("createdTime",1591804860000)
            .whereGreaterThan("createdTime",calendar)
            .whereLessThan("createdTime",endcalendar)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<InsertRecord>()
                    for (document in task.result!!) {

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

    override suspend fun getCalendarTrainingCardioRecord(calendar: Long, endcalendar: Long): Result<List<CardioRecord>>  = suspendCoroutine { continuation ->

        var timeNow: Long = Calendar.getInstance().timeInMillis
        var timePeriod: Long = timeNow + 60000000000

        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLES_USER)
            .document("${UserManger.userID}")
            .collection("cardio")
            .document("self")
            .collection("record")
            .whereGreaterThan("createdTime",calendar)
            .whereLessThan("createdTime",endcalendar)
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


}