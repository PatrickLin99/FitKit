package com.patrick.fittracker

import android.app.Application
import com.patrick.fittracker.data.source.FitTrackerRepository
import com.patrick.fittracker.util.ServiceLocator
import kotlin.properties.Delegates

class FitTrackerApplication : Application() {

    // Depends on the flavor,
    val repository: FitTrackerRepository
        get() = ServiceLocator.provideRepository(this)

    companion object {
        var instance: FitTrackerApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun isLiveDataDesign() = true

}