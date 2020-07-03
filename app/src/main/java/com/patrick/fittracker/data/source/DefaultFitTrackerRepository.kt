package com.patrick.fittracker.data.source

import androidx.lifecycle.MutableLiveData
import com.patrick.fittracker.data.Article
import com.patrick.fittracker.data.Result
import com.patrick.fittracker.data.SelectedMuscleGroup
import com.patrick.fittracker.group.MuscleGroupTypeFilter

class DefaultFitTrackerRepository(private val remoteDataSource: FitTrackerDataSource,
                                  private val localDataSource: FitTrackerDataSource
) : FitTrackerRepository {
    override suspend fun getSelectedMuscleGroupMenu(group: MuscleGroupTypeFilter): Result<SelectedMuscleGroup> {
        return remoteDataSource.getSelectedMuscleGroupMenu(group)
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