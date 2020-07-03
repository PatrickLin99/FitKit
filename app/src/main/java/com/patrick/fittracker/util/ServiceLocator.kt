package com.patrick.fittracker.util

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.patrick.fittracker.data.source.DefaultFitTrackerRepository
import com.patrick.fittracker.data.source.FitTrackerDataSource
import com.patrick.fittracker.data.source.FitTrackerRepository
import com.patrick.fittracker.data.source.local.FitTrackerLocalDataSource
import com.patrick.fittracker.data.source.remote.FitTrackerRemoteDataSource

object ServiceLocator {

    @Volatile
    var repository: FitTrackerRepository? = null
        @VisibleForTesting set

    fun provideRepository(context: Context): FitTrackerRepository {
        synchronized(this) {
            return repository
                ?: repository
                ?: createPublisherRepository(context)
        }
    }

    private fun createPublisherRepository(context: Context): FitTrackerRepository {
        return DefaultFitTrackerRepository(
            FitTrackerRemoteDataSource,
            createLocalDataSource(context)
        )
    }

    private fun createLocalDataSource(context: Context): FitTrackerDataSource {
        return FitTrackerLocalDataSource(context)
    }
}