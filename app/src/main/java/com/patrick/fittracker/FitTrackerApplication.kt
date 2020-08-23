package com.patrick.fittracker

import android.app.Application
import android.content.Context
import com.patrick.fittracker.MyApplication.Companion.appContext
import com.patrick.fittracker.data.source.FitTrackerRepository
import com.patrick.fittracker.util.ServiceLocator
import kotlin.properties.Delegates

class FitTrackerApplication : Application() {

    val repository: FitTrackerRepository
        get() = ServiceLocator.provideRepository(this)

    companion object {
        var instance: FitTrackerApplication by Delegates.notNull()
        lateinit var appContext: Context

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appContext = applicationContext
    }

    fun isLiveDataDesign() = true

}