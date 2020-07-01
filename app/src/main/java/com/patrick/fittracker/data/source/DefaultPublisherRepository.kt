package com.patrick.fittracker.data.source

import androidx.lifecycle.MutableLiveData
import com.patrick.fittracker.data.Article
import com.patrick.fittracker.data.Author

class DefaultPublisherRepository(private val remoteDataSource: PublisherDataSource,
                                 private val localDataSource: PublisherDataSource
) : PublisherRepository {
    override suspend fun getSelectedMuscleGroupMenu(id: String): List<String> {
        return remoteDataSource.getSelectedMuscleGroupMenu(id)
    }

//    override suspend fun loginMockData(id: String): Result<Author> {
//        return localDataSource.login(id)
//    }
//
//    override suspend fun getArticles(): Result<List<Article>> {
//        return remoteDataSource.getArticles()
//    }
//
//    override fun getLiveArticles(): MutableLiveData<List<Article>> {
//        return remoteDataSource.getLiveArticles()
//    }
//
//    override suspend fun publish(article: Article): Result<Boolean> {
//        return remoteDataSource.publish(article)
//    }
//
//    override suspend fun delete(article: Article): Result<Boolean> {
//        return remoteDataSource.delete(article)
//    }
}