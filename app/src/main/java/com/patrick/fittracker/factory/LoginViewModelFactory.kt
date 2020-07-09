package com.patrick.fittracker.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.patrick.fittracker.data.AddTrainingRecord
import com.patrick.fittracker.data.User
import com.patrick.fittracker.data.source.FitTrackerRepository
import com.patrick.fittracker.login.LoginViewModel

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(
    private val repository: FitTrackerRepository,
    private val user: User
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                repository, user
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}