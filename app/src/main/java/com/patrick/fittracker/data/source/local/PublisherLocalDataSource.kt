package com.patrick.fittracker.data.source.local

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.patrick.fittracker.data.Author
import com.patrick.fittracker.data.source.PublisherDataSource

class PublisherLocalDataSource(val context: Context) : PublisherDataSource {
    override suspend fun getSelectedMuscleGroupMenu(id: String): List<String> {
        TODO("Not yet implemented")
    }

//    override suspend fun login(id: String): Result<Author> {
//        return when (id) {
//            "waynechen323" -> Result.Success((Author(
//                id,
//                "AKA小安老師",
//                "wayne@school.appworks.tw"
//            )))
//            "dlwlrma" -> Result.Success((Author(
//                id,
//                "IU",
//                "dlwlrma@school.appworks.tw"
//            )))
//            //TODO add your profile here
//            else -> Result.Fail("You have to add $id info in local data source")
//        }
//    }
//
//    override suspend fun getArticles(): Result<List<Article>> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getLiveArticles(): MutableLiveData<List<Article>> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override suspend fun publish(article: Article): Result<Boolean> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override suspend fun delete(article: Article): Result<Boolean> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
}