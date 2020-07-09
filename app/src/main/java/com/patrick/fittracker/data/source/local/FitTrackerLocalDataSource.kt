package com.patrick.fittracker.data.source.local

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.patrick.fittracker.data.*
import com.patrick.fittracker.data.source.FitTrackerDataSource
import com.patrick.fittracker.group.MuscleGroupTypeFilter
import com.patrick.fittracker.record.selftraining.SetOrderFilter

class FitTrackerLocalDataSource(val context: Context) : FitTrackerDataSource {
    override suspend fun getSelectedMuscleGroupMenu(group: MuscleGroupTypeFilter): Result<SelectedMuscleGroup> {
        TODO("Not yet implemented")
    }

    override suspend fun getSetOrderNum(group: SetOrderFilter): Result<RecordSetOrder> {
        TODO("Not yet implemented")
    }

    override suspend fun getCardioSelection(): Result<List<Cardio>> {
        TODO("Not yet implemented")
    }

    override suspend fun getClassOption(): Result<List<ClassOption>> {
        TODO("Not yet implemented")
    }

    override suspend fun addRecord(addTrainingRecord: AddTrainingRecord): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecord(muscleKey: String): Result<List<AddTrainingRecord>> {
        TODO("Not yet implemented")
    }

    override suspend fun addClassRecord(addTrainingRecord: AddTrainingRecord): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getClassRecord(classKey: String): Result<List<AddTrainingRecord>> {
        TODO("Not yet implemented")
    }

    override suspend fun addCardioRecord(addTrainingRecord: AddTrainingRecord): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun addUserInfo(addTrainingRecord: AddTrainingRecord): Result<Boolean> {
        TODO("Not yet implemented")
    }


}