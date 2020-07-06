package com.patrick.fittracker.data.source.remote


import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.patrick.fittracker.FitTrackerApplication
import com.patrick.fittracker.R
import com.patrick.fittracker.data.*
import com.patrick.fittracker.data.source.FitTrackerDataSource
import com.patrick.fittracker.group.MuscleGroupTypeFilter
import com.patrick.fittracker.record.SetOrderFilter
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
//                    continuation.resume(Result.Fail(PublisherApplication.instance.getString(R.string.you_know_nothing)))
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
//                    continuation.resume(Result.Fail(PublisherApplication.instance.getString(R.string.you_know_nothing)))
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





//    override suspend fun login(id: String): Result<Author> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override suspend fun getArticles(): Result<List<Article>> = suspendCoroutine { continuation ->
//        FirebaseFirestore.getInstance()
//            .collection(PATH_ARTICLES)
//            .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
//            .get()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val list = mutableListOf<Article>()
//                    for (document in task.result!!) {
//                        Logger.d(document.id + " => " + document.data)
//
//                        val article = document.toObject(Article::class.java)
//                        list.add(article)
//                    }
//                    continuation.resume(Result.Success(list))
//                } else {
//                    task.exception?.let {
//
//                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
//                        continuation.resume(Result.Error(it))
//                        return@addOnCompleteListener
//                    }
//                    continuation.resume(Result.Fail(PublisherApplication.instance.getString(R.string.you_know_nothing)))
//                }
//            }
//    }
//
//    override fun getLiveArticles(): MutableLiveData<List<Article>> {
//
//        val liveData = MutableLiveData<List<Article>>()
//
//        FirebaseFirestore.getInstance()
//            .collection(PATH_ARTICLES)
//            .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
//            .addSnapshotListener { snapshot, exception ->
//
//                Logger.i("addSnapshotListener detect")
//
//                exception?.let {
//                    Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
//                }
//
//                val list = mutableListOf<Article>()
//                for (document in snapshot!!) {
//                    Logger.d(document.id + " => " + document.data)
//
//                    val article = document.toObject(Article::class.java)
//                    list.add(article)
//                }
//
//                liveData.value = list
//            }
//        return liveData
//    }
//
//    override suspend fun publish(article: Article): Result<Boolean> = suspendCoroutine { continuation ->
//        val articles = FirebaseFirestore.getInstance().collection(PATH_ARTICLES)
//        val document = articles.document()
//
//        article.id = document.id
//        article.createdTime = Calendar.getInstance().timeInMillis
//
//        document
//            .set(article)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Logger.i("Publish: $article")
//
//                    continuation.resume(Result.Success(true))
//                } else {
//                    task.exception?.let {
//
//                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
//                        continuation.resume(Result.Error(it))
//                        return@addOnCompleteListener
//                    }
//                    continuation.resume(Result.Fail(PublisherApplication.instance.getString(R.string.you_know_nothing)))
//                }
//            }
//    }
//
//    override suspend fun delete(article: Article): Result<Boolean> = suspendCoroutine { continuation ->
//
//        when {
//            article.author?.id == "waynechen323"
//                    && article.tag.toLowerCase(Locale.TAIWAN) != "test"
//                    && article.tag.trim().isNotEmpty() -> {
//
//                continuation.resume(Result.Fail("You know nothing!! ${article.author?.name}"))
//            }
//            else -> {
//                FirebaseFirestore.getInstance()
//                    .collection(PATH_ARTICLES)
//                    .document(article.id)
//                    .delete()
//                    .addOnSuccessListener {
//                        Logger.i("Delete: $article")
//
//                        continuation.resume(Result.Success(true))
//                    }.addOnFailureListener {
//                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
//                        continuation.resume(Result.Error(it))
//                    }
//            }
//        }
//
//    }

}