package com.patrick.fittracker.util

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.patrick.fittracker.data.source.DefaultPublisherRepository
import com.patrick.fittracker.data.source.PublisherDataSource
import com.patrick.fittracker.data.source.PublisherRepository
import com.patrick.fittracker.data.source.local.PublisherLocalDataSource
import com.patrick.fittracker.data.source.remote.PublisherRemoteDataSource

object ServiceLocator {

    @Volatile
    var repository: PublisherRepository? = null
        @VisibleForTesting set

    fun provideRepository(context: Context): PublisherRepository {
        synchronized(this) {
            return repository
                ?: repository
                ?: createPublisherRepository(context)
        }
    }

    private fun createPublisherRepository(context: Context): PublisherRepository {
        return DefaultPublisherRepository(
            PublisherRemoteDataSource,
            createLocalDataSource(context)
        )
    }

    private fun createLocalDataSource(context: Context): PublisherDataSource {
        return PublisherLocalDataSource(context)
    }
}