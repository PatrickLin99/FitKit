package com.patrick.fittracker.data.source

import androidx.lifecycle.MutableLiveData
import com.patrick.fittracker.data.Article
import com.patrick.fittracker.data.Result
import com.patrick.fittracker.data.SelectedMuscleGroup
import com.patrick.fittracker.group.MuscleGroupTypeFilter

interface FitTrackerDataSource {

    suspend fun getSelectedMuscleGroupMenu (group: MuscleGroupTypeFilter): Result<SelectedMuscleGroup>

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